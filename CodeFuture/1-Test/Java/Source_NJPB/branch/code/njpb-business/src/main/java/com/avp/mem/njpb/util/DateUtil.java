/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amber Wang on 2017-06-19 下午 01:29.
 */
public class DateUtil {

//    /**
//     * 根据当前日期获得所在周的日期区间（周一和周日日期）
//     *
//     * @return
//     * @author zhaoxuepu
//     * @throws ParseException
//     */
//    public static String getTimeInterval(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
//        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
//        if (1 == dayWeek) {
//            cal.add(Calendar.DAY_OF_MONTH, -1);
//        }
//        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
//        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
//        cal.setFirstDayOfWeek(Calendar.MONDAY);
//        // 获得当前日期是一个星期的第几天
//        int day = cal.get(Calendar.DAY_OF_WEEK);
//        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
//        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
//        String imptimeBegin = sdf.format(cal.getTime());
//        // System.out.println("所在周星期一的日期：" + imptimeBegin);
//        cal.add(Calendar.DATE, 6);
//        String imptimeEnd = sdf.format(cal.getTime());
//        // System.out.println("所在周星期日的日期：" + imptimeEnd);
//        return imptimeBegin + "," + imptimeEnd;
//    }

    /**
     * 获取指定日期的本周开始时间
     *
     * @return Date
     * @author Amber Wang
     */
    public static Date getThisWeekBeginTime(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += MagicNumber.SEVEN;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取指定日期的本周结束时间
     *
     * @return
     * @author Amber Wang
     */
    public static Date getThisWeekEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekBeginTime(date));
        cal.add(Calendar.DAY_OF_WEEK, MagicNumber.SIX);

        cal.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        cal.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        cal.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        return cal.getTime();
    }

    /**
     * 获取指定日期的上周开始时间
     *
     * @return Date
     * @author Amber Wang
     */
    public static Date getLastWeekBeginTime(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, offset1 - MagicNumber.SEVEN);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取指定日期的上周结束时间
     *
     * @return
     * @author Amber Wang
     */
    public static Date getLastWeekEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = MagicNumber.SEVEN - dayOfWeek;
        calendar.add(Calendar.DATE, offset - MagicNumber.SEVEN);

        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        calendar.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        calendar.set(Calendar.SECOND, MagicNumber.FIVE_NINE);

        return calendar.getTime();
    }

//    public static void main(String[] args) {
//
//        System.out.println(DateUtil.getLastWeekBeginTime(getLastWeekBeginTime(new Date())));
//
//    }

    /**
     * 根据日期判断当月第几周
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static int getWeekOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week;
    }

    /**
     * 根据日期判断当年第几周
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    public static Date getTodayStartTime() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        return today.getTime();
    }

    /**
     * 获取指定日期的年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }


    /**
     * 获取指定日期的月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * @return Date
     * @author Amber Wang
     */
    public static Date getLastMonthBeginTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //设置为1号,当前日期既为本月第一天

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date lastBeginDate = calendar.getTime();
        return lastBeginDate;
    }

    /**
     * @return Date
     * @author Amber Wang
     */
    public static Date getLastMonthEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        calendar.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        calendar.set(Calendar.SECOND, MagicNumber.FIVE_NINE);

        Date lastBeginDate = calendar.getTime();
        return lastBeginDate;
    }

    /**
     * 获取当前时间前一天的日期
     */
    public static Date getBeforeDayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.ONE_TWO);
        Date beforeDayTime = calendar.getTime();
        return beforeDayTime;
    }

    /**
     * 获取当前时间前一天的开始日期
     */
    public static Date getBeforeDayBeginTIme() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.ONE_TWO);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beforeDayBeginTime = calendar.getTime();
        return beforeDayBeginTime;
    }

    /**
     * 获取当前时间前一天的结束日期
     */
    public static Date getBeforeDayEndTIme() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        calendar.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        calendar.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        Date beforedayEndTime = calendar.getTime();
        return beforedayEndTime;
    }


    /**
     * 获取当前时间前天的开始日期
     */
    public static Date getBeforeYesterdayBeginTIme() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 2);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.ONE_TWO);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beforeDayBeginTime = calendar.getTime();
        return beforeDayBeginTime;
    }

    /**
     * 获取当前时间前一天的结束日期
     */
    public static Date getBeforeYesterdayEndTIme() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 2);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        calendar.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        calendar.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        Date beforedayEndTime = calendar.getTime();
        return beforedayEndTime;
    }


    /**
     * 获取当前时间前2天的开始日期
     */
    public static Date getBeforeThreedayBeginTIme() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - MagicNumber.THREE);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.ONE_TWO);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beforeDayBeginTime = calendar.getTime();
        return beforeDayBeginTime;
    }

    /**
     * 获取当前时间前2天的结束日期
     */
    public static Date getBeforeThreedayEndTIme() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - MagicNumber.THREE);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        calendar.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        calendar.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        Date beforedayEndTime = calendar.getTime();
        return beforedayEndTime;
    }

    /**
     * 某月最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        cal.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        cal.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        //格式化日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String lastDayOfMonth = sdf.format(cal.getTime());

        return cal.getTime();
    }

    /**
     * 某月第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        //格式化日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String firstDayOfMonth = sdf.format(cal.getTime());
        return cal.getTime();
    }


    /**
     * 根据当前日期获取上周周一 0点0分0秒
     */
    public static Date getBeforeWeekBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int dateSpace = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, dateSpace - MagicNumber.SEVEN);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beforeWeekBeginTime = calendar.getTime();
        return beforeWeekBeginTime;
    }

    /**
     * 根据当前时间获取上周周日 23点59分59秒
     */
    public static Date getBeforeWeekEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int dateSpace = MagicNumber.SEVEN - dayOfWeek;
        calendar.add(Calendar.DATE, dateSpace - MagicNumber.SEVEN);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        calendar.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        calendar.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        Date beforeWeekEndTime = calendar.getTime();
        return beforeWeekEndTime;
    }

    /**
     * 根据当前日期获取上月初一 0点0分0秒
     */
    public static Date getBeforeMonthBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, Calendar.MONTH - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date beforeWeekBeginTime = calendar.getTime();
        return beforeWeekBeginTime;
    }

    /**
     * 根据当前时间获取上月月末 23点59分59秒
     */
    public static Date getBeforeMonthEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, Calendar.MONTH - 1);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        calendar.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        calendar.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        Date beforeWeekEndTime = calendar.getTime();
        return beforeWeekEndTime;
    }

    /**
     * 根据当前时间获取本月月末 23点59分59秒
     */
    public static Date getMonthEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Calendar.MONTH);
        calendar.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        calendar.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        calendar.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        Date monthEndTime = calendar.getTime();
        return monthEndTime;
    }

    public static Date getonthOfDate(Date date) {
        Calendar para = Calendar.getInstance(java.util.Locale.CHINA);
        para.setTime(date);
        para.set(Calendar.DATE, para.getActualMaximum(Calendar.DAY_OF_MONTH));
        para.set(Calendar.HOUR_OF_DAY, MagicNumber.TWO_THREE);
        para.set(Calendar.MINUTE, MagicNumber.FIVE_NINE);
        para.set(Calendar.SECOND, MagicNumber.FIVE_NINE);
        return para.getTime();
    }


    public static int getMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据当前时间获取上周是本年第几周
     */
    public static int getBeforeWeekOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfYer = calendar.get(Calendar.WEEK_OF_YEAR) - 1;
        return weekOfYer;
    }

    /**
     * 检验时间是否在时间段内
     */
    public static boolean checkTime(Date date, Date dateBegin, Date dateEnd) {
        if (date == null || dateBegin == null || dateEnd == null) {
            return false;
        }
        return dateBegin.before(date) && dateEnd.after(date);
    }
}
