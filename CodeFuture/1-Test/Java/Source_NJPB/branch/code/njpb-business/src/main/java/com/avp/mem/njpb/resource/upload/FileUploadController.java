/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.upload;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.entity.basic.ObjFile;
import com.avp.mem.njpb.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * image upload
     *
     * @param image
     * @return Map<String, Object>
     * @throws IOException
     */
    @RequestMapping(value = "files/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<RestBody> uploadImage(@RequestParam(value = "image", required = false) MultipartFile image)
            throws IOException {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjFile objFile = fileUploadUtil.uploadImage(image);

            builder.setResultEntity(objFile, ResponseCode.CREATE_SUCCEED);
            LOGGER.debug("图片上传成功,文件ID为:{}", objFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * 上传多张照片
     *
     * @param images
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "files/uploadImages", method = RequestMethod.POST)
    public ResponseEntity<RestBody> uploadImages(@RequestParam(value = "images", required = false) MultipartFile[] images)
            throws IOException {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        List<Integer> fileIdList = new ArrayList<>();
        try {
            for (MultipartFile image : images) {
                ObjFile objFile = fileUploadUtil.uploadImage(image);
                fileIdList.add(objFile.getId());
                LOGGER.debug("图片上传成功，源文件名称为：{},ObjFile ID为：{}", image.getOriginalFilename(), objFile.getId());
            }

            builder.setResultEntity(fileIdList, ResponseCode.CREATE_SUCCEED);
            LOGGER.debug("图片上传成功,数据量为：{}", fileIdList.size());
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

    /**
     * file upload
     *
     * @param file
     * @return Map<String, Object>
     * @throws IOException
     */
    @RequestMapping(value = "/upload_file", method = RequestMethod.POST)
    public ResponseEntity<RestBody> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file)
            throws IOException {
        LOGGER.debug("url:upload_file,file :{}", file);
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        try {
            ObjFile objFile = fileUploadUtil.uploadFile(file);
            //objFile.setUrlPrefix(this.urlPrefix);
            builder.setResultEntity(objFile, ResponseCode.CREATE_SUCCEED);
            LOGGER.debug("文件上传成功,文件ID为:{}", objFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            builder.setResponseCode(ResponseCode.FAILED);
        }
        return builder.getResponseEntity();
    }

}
