package com.avantport.avp.njpb.uitls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by UI-PC-2 on 2017/3/2.
 */

public class FileUtil {

    public static long getSDFreeSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize)/1024 /1024; //单位MB
    }

    // 验证字符串是否为正确路径名的正则表达式
    private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
    // 通过 sPath.matches(matches) 方法的返回值判断是否正确
    // sPath 为路径字符串
    private static boolean flag = false;
    private static File file;

    /**
     * 写入文件
     * @param content 内容
     * @param file
     * @throws IOException
     */
    public static String writeStr(String content, File file) throws IOException {
        createDir(file.getAbsolutePath());
        PrintStream outputStream = new PrintStream(new FileOutputStream(file));
        outputStream.print(content);
        outputStream.close();
        return file.getAbsolutePath();
    }

    /**
     * base64转码
     *
     * @param path 路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        if (TextUtils.isEmpty(path)) return "";
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * @param base64Code
     * @param savePath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        if (TextUtils.isEmpty(base64Code)) throw new Exception("base64Code is null!");
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        createDir(savePath);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }

    public String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param deletePath
     * @return
     */
    public static boolean DeleteFolder(String deletePath) {
        flag = false;
        if (deletePath.matches(matches)) {
            file = new File(deletePath);
            if (!file.exists()) {// 判断目录或文件是否存在
                return flag; // 不存在返回 false
            } else {

                if (file.isFile()) {// 判断是否为文件
                    return deleteFile(deletePath);// 为文件时调用删除文件方法
                } else {
                    return deleteDirectory(deletePath);// 为目录时调用删除目录方法
                }
            }
        } else {
            return false;
        }
    }



    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param dirPath
     * @return
     */
    public static boolean deleteDirectory(String dirPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }
        File dirFile = new File(dirPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
        for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
            if (files[i].isFile()) {// 删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;// 如果删除失败，则跳出
            } else {// 运用递归，删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;// 如果删除失败，则跳出
            }
        }
        if (!flag)
            return false;
        if (dirFile.delete()) {// 删除当前目录
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        flag = false;
        file = new File(filePath);
        if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
            file.delete();// 文件删除
            flag = true;
        }
        return flag;
    }

    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            return true;
        } else {
            return false;
        }
    }


    public static String saveBitmapToFile(Bitmap baseBitmap) {
        FileOutputStream b = null;
        String name = new DateFormat().format("MMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        File file = new File("/sdcard/avantPort/");
        String fileName = "/sdcard/avantPort/" + name;
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            b = new FileOutputStream(fileName);
            baseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(b!=null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  fileName;
    }


    public static  String saveBitmapToFile(Intent data){
        String name = new DateFormat().format("MMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
        FileOutputStream b = null;
        File file = new File("/sdcard/avantPort/");
        if(!file.exists()){
            file.mkdirs();
        }
        String fileName = "/sdcard/avantPort/"+name;
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(b!=null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  fileName;
    }




}
