/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.util;

import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.basic.ObjFile;
import com.avp.mem.njpb.repository.basic.ObjFileRepository;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;

@Service
public class FileUploadUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);

    @Value("${business.service.file.resource}")
    private String uploadAddress;

    @Value("${business.service.file.url-prefix}")
    private String urlPrefix;
    @Value("${business.service.file.url-pre}")
    private String urlPre;

    @Value("${business.service.file.app}")
    private String urlApp;

    public File uploadedFile;

    @Autowired
    private ObjFileRepository objFileRepository;

    /**
     * image upload
     *
     * @param file
     * @return ObjFile
     * @throws IOException
     */
    public ObjFile uploadImage(MultipartFile file) throws IOException {
        ObjFile objFile = new ObjFile();
        if (file == null) {
            LOGGER.debug("上传图片请求参数缺失");
            throw new IOException("上传图片请求参数缺失");
        }
        Integer uid = SecurityUtils.getLoginUserId();

        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();

        int fileSize = ((Long) file.getSize()).intValue();

        LOGGER.debug("上传的图片信息fileName:{},fileType:{},fileSize:{}", fileName, fileType, fileSize);

        byte[] fileBytes = file.getBytes();
        String sha1sum = this.sha1sum(fileBytes);
        LOGGER.debug("该图片的MD5值为: {}", sha1sum);

        // 查询数据库中是否已经存在该图片
        objFile = this.getByFileId(sha1sum);

        if (objFile == null) {
            // 数据库不存待上传的图片，执行上传操作
            LOGGER.debug("数据库不存在待上传图片:{},执行上传操作", fileName);
            // generate thumbnail
            this.generateThumbnailAndCopyOrigin(this.multipartToFile(file), sha1sum, "jpg",
                    "thumb_" + sha1sum + ".jpg", MagicNumber.ONE_FIVE_ZERO, MagicNumber.ONE_ONE_SEVEN);

            fileType = "jpg";
            String fileUrl = urlPrefix + sha1sum + "." + fileType;
            LOGGER.debug("文件的URL为:{}", fileUrl);

            ObjFile of = new ObjFile(sha1sum, fileName, fileSize, "thumb_" + sha1sum, fileType, uid);

            of.setFileUrl(fileUrl);
            objFile = objFileRepository.save(of);
        } else {
            LOGGER.debug("待上传的文件已经存在于数据库：{}", objFile.getId());
        }

        return objFile;
    }

    /**
     * APP端文件上传使用
     *
     * @param base64File
     * @param fileName
     * @return ObjFile
     * @throws IOException
     */
    public ObjFile uploadAppFile(String base64File, String fileName) throws IOException {
        ObjFile objFile = new ObjFile();
        LOGGER.debug("开始上传文件 : {}", fileName);
        int uid = SecurityUtils.getLoginUserId();
        BASE64Decoder decoder = new BASE64Decoder();

//        byte[] buffer = decoder.decodeBuffer(base64File.substring(23));
        byte[] buffer = decoder.decodeBuffer(base64File);
        for (int i = 0; i < buffer.length; ++i) {
            if (buffer[i] < 0) {// 调整异常数据
                buffer[i] += MagicNumber.TWO_FIVE_SIX;
            }
        }

        String sha1sum = this.sha1sum(buffer);
        LOGGER.debug("该图片的MD5值为: {}", sha1sum);
        objFile = this.getByFileId(sha1sum);

        if (objFile == null) {
            LOGGER.debug("数据库不存在待上传图片:{},执行上传操作", fileName);

            String fileType = "jpg";
            File file = new File(uploadAddress);

            if (!file.exists()) {
                file.mkdirs();
            }

            String fileUrl = urlPrefix + sha1sum + ".jpg";
            LOGGER.debug("APP上传的文件的fileUrl为:{}", fileUrl);

            String filePath = uploadAddress + sha1sum + "." + fileType;
            LOGGER.debug("文件上传,路径为:{}", filePath);

            OutputStream out = new FileOutputStream(filePath);
            out.write(buffer);

            out.flush();
            out.close();

            BufferedImage img = new BufferedImage(MagicNumber.ONE_FIVE_ZERO, MagicNumber.ONE_SEVEN_ZERO, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(
                    ImageIO.read(new File(filePath)).getScaledInstance(MagicNumber.ONE_FIVE_ZERO, MagicNumber.ONE_SEVEN_ZERO, MagicNumber.FOUR), 0, 0, null);

            String newName = "thumb_" + sha1sum + ".jpg";
            LOGGER.debug("准备生成缩略图文件,文件名为:{}", newName);

            ImageIO.write(img, "jpg", new File(uploadAddress + newName));

            return new ObjFile(sha1sum, fileName, new Long(new File(filePath).length()).intValue(), "thumb_" + sha1sum, fileType, uid);
        }
        LOGGER.debug("数据库已经存在该文件,文件ID为:{},不要再次上传图片", objFile.getId());
        return objFile;

    }

    /**
     * file upload for web
     *
     * @param file
     * @return ObjFile
     * @throws Exception
     */
    public ObjFile uploadFile(MultipartFile file) throws Exception {
        ObjFile objFile = new ObjFile();
        if (file == null) {
            LOGGER.debug("上传文件方法参数缺失");
            throw new Exception("上传文件方法参数缺失！");
        }

        int uid = SecurityUtils.getLoginUserId();
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        Long fileSize1 = file.getSize();

        int fileSize = fileSize1.intValue();

        LOGGER.debug("上传的文件信息fileName:{},fileType:{},fileSize:{}", fileName, fileType, fileSize);

        if (fileName.trim().contains(".")) {
            fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            LOGGER.debug("上传的文件后缀名为:{}", fileType);
        }

        // 强制所有上传的视频文件后缀为mp4
//        fileType = "mp4";

        byte[] fileBytes = file.getBytes();
        String sha1sum = this.sha1sum(fileBytes);
        LOGGER.debug("该文件的MD5值为: {}", sha1sum);

        // 查询数据库中是否已经存在该文件
        // objFile = this.getByFileId(sha1sum);
        //   if (objFile == null) {
        // 数据库不存待上传的文件，执行上传操作
        LOGGER.debug("数据库不存在待上传的文件，执行上传操作 ");

        // generate thumbnail
        this.copyOriginFile(this.multipartToFile(file), file.getOriginalFilename(), fileType, file.getOriginalFilename());

        String fileUrl = urlPre + file.getOriginalFilename();
        //+ "." + fileType;

        LOGGER.debug("上传的文件的URL:{}", fileUrl);

        ObjFile of = new ObjFile(sha1sum, fileName, fileSize, null, fileType, uid);
        of.setFileUrl(fileUrl);
        objFile = objFileRepository.save(of);


        FileInputStream fis = new FileInputStream(urlApp+ "/" +fileName);

        FileOutputStream fos = new FileOutputStream(urlApp + "/" + "NJPB_handled.apk");
        int len;
        byte[] arr = new byte[MagicNumber.ONE_ZERO_TWO_FOUR * MagicNumber.EIGHT];
        //自定义字节数组

        while ((len = fis.read(arr)) != -1) {
            //fos.write(arr);
            fos.write(arr, 0, len);
            //写出字节数组写出有效个字节个数
        }
        //IO流(定义小数组)
        //write(byte[] b)
        //write(byte[] b, int off, int len)写出有效的字节个数

        fis.close();
        fos.close();

        // }
        return objFile;
    }

    public ObjFile getByFileId(String fileId) {
        return objFileRepository.findOneByFileId(fileId);
    }

    public String byteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & MagicNumber.ZERO_X_F_F) + MagicNumber.ZERO_X_ONE_ZERO_ZERO, MagicNumber.ONE_SIX).substring(1));
        }
        return sb.toString();
    }

    public String sha1sum(byte[] data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md.update(data, 0, data.length);
        return byteToHex(md.digest());
    }

    public void generateThumbnailAndCopyOrigin(File file, String originHash, String originType, String newName,
                                               int width, int height) throws IOException {
        FileUtils.copyFile(file, new File(uploadAddress + originHash + "." + originType));

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.createGraphics().drawImage(ImageIO.read(file).getScaledInstance(width, height, MagicNumber.FOUR), 0, 0,
                null);
        ImageIO.write(img, "jpg", new File(uploadAddress + newName));
        uploadedFile.delete();
    }

    /**
     * 描述：copy文件
     *
     * @param file, String originHash, String originType, String originName
     * @return void
     * @throws IOException
     */
    public void copyOriginFile(File file, String originName, String originType, String originName1) throws IOException {
        File folder = new File(urlApp);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // String type = "";
        // if (originName.trim().contains(".")) {
        // type = "." +
        // originName.toString().substring(originName.lastIndexOf(".") + 1);
        // }
        LOGGER.debug("文件的type是: {}", originType);
        String type = "." + originType;

        FileUtils.copyFile(file, new File(urlApp + originName));
        uploadedFile.delete();
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File file = new File(uploadAddress);

        if (!file.exists()) {
            LOGGER.debug("文件上传的指定目录不存在,新建文件夹");
            file.mkdirs();
        }
        uploadedFile = File.createTempFile("preview_", ".temp", file.getAbsoluteFile());
        multipart.transferTo(uploadedFile);
        return uploadedFile;
    }

    public String convertToBase64(File file) {
        // Reading a Image file from file system
        try {
            BufferedImage buffImage = ImageIO.read(file);

            String imageDataString = encodeToString(buffImage, "png");

            return imageDataString;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.debug("Image not found");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            LOGGER.debug("Exception while reading the Image ");
        }
        return null;
    }

    /**
     * Encode image to Base64 string
     *
     * @param image The image to Base64 encode
     * @param type  jpeg, bmp, ...
     * @return Base64 encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            // BASE64Encoder encoder = new BASE64Encoder();
            // imageString = encoder.encode(imageBytes);
            imageString = Base64.getEncoder().encodeToString(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    /**
     * encode image to base64
     *
     * @param
     * @return
     * @throws IOException
     */
    // @RequestMapping(value = "/encodeBase64Image", method = RequestMethod.GET)
    // public Map<String, Object> covertBase64(
    // @RequestParam(value = "thumbPath", required = true) String thumbPath,
    // @RequestParam(value = "originPath", required = true) String originPath)
    // throws IOException {
    // Map<String, Object> ret = new HashMap<String, Object>();
    // String returnThumbCode = "", returnOriginCode = "";
    // try {
    // File f1 = new File(uploadAddress + thumbPath);
    // if (f1.exists() && !f1.isDirectory()) {
    // returnThumbCode = fileUploadService.convertToBase64(f1);
    // }
    // File f2 = new File(uploadAddress + originPath);
    // if (f2.exists() && !f2.isDirectory()) {
    // returnOriginCode = fileUploadService.convertToBase64(f2);
    // }
    // ret.put("error", 0);
    // ret.put("returnThumbCode", returnThumbCode);
    // ret.put("returnOriginCode", returnOriginCode);
    // } catch (Exception e) {
    // ret.clear();
    // ret.put("error", -1);
    // ret.put("errmsg", e.getMessage());
    // }
    // return ret;
    // }
    public String getStringDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return lpad(MagicNumber.FOUR, year) + "" + lpad(2, month) + "" + lpad(2, day);
    }

    private String lpad(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }
}
