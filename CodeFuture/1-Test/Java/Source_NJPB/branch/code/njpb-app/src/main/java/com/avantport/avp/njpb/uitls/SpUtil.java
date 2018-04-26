package com.avantport.avp.njpb.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.util.Base64;

import com.avantport.avp.njpb.ui.base.NJPBApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by len on 2017/8/25.
 * sp帮组类
 */

public class SpUtil {


    public final static String SETTING = "Setting";

    //保存token值
    public static void saveValue(String key, String value) {
        SharedPreferences.Editor edit = NJPBApplication.context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        edit.putString(key, value);
        edit.commit();

    }
    public static void saveLongValue(String key, long value) {
        SharedPreferences.Editor edit = NJPBApplication.context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        edit.putLong(key, value);
        edit.commit();
    }
    public static long getLongValue(String key) {
        SharedPreferences sp = NJPBApplication.context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        long value = sp.getLong(key, (long) 0);
        return value;
    }

    //取出token值
    public static String getValue(String key) {
        SharedPreferences sp = NJPBApplication.context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        String value = sp.getString(key, "");
        return value;

    }

    //删除指定的数据
    public static void removeValue(String key) {
        SharedPreferences.Editor edit = NJPBApplication.context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        edit.remove(key);
        edit.commit();
    }

    //清空所有保存的数据
    public static void cleanValue() {
        SharedPreferences.Editor edit = NJPBApplication.context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        edit.clear();
        edit.commit();
    }

    //储存用户的信息,写入用户信息对象
    public static void saveObject(String key, Object object) {

        SharedPreferences.Editor edit = NJPBApplication.context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        //创建对象输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            edit.putString(key, objectVal);
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获取用户信息对象
    public static <T extends Object> T getObjFromSp(String key) {

        SharedPreferences preferences = NJPBApplication.context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        byte[] bytes = Base64.decode(preferences.getString(key, ""), Base64.DEFAULT);
        ByteArrayInputStream bis;
        ObjectInputStream ois = null;
        T obj = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    // TODO: 2017/9/2  
    //将图片压缩保存
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
}
