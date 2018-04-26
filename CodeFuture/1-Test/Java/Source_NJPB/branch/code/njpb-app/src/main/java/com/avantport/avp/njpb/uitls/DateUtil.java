package com.avantport.avp.njpb.uitls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by len on 2017/8/15.
 */

public class DateUtil {


    //获得今天的日期
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = sdf.format(new Date());
        return date;

    }

    //获得当前的月份
    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date());
    }


    public static String getDateTime(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp);
        return date;
    }

    /**
     * 将时间添加一秒
     *
     * @param timeStamp 时间戳
     * @return
     */

    public static String formatDate(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String date = sdf.format((timeStamp / 1000 + 1) * 1000);

        return date;

    }

    public static String formatLastDate(long timeStamp) {//上拉加载更多的时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String date = sdf.format(timeStamp);

        return date;

    }

    /**
     * 得到时间  HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return
     */

    public static String getTime(long timeStamp) {

        String time = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String date = sdf.format(timeStamp * 1000);

        String[] split = date.split("\\s");

        if (split.length > 1) {

            time = split[1];

        }

        return time;

    }

    /**
     * 返回unix时间戳 (1970年至今的秒数)
     *
     * @return
     */

    public static long getUnixStamp() {

        return System.currentTimeMillis() / 1000;

    }


    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */

    public static String timeStampToStr(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String date = sdf.format(timeStamp * 1000);

        return date;

    }


    public static String testTime1(String dateStr) {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);//输入的被转化的时间格式
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//需要转化成的时间格式
        Date date1 = null;
        try {
            date1 = dff.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df1.format(date1);
    }


    public static String getNowDate() {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
            date = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
