package com.chaychan.news.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ChayChan
 * @description: 和时间相关的工具类
 * @date 2017/6/19  20:08
 */

public class TimeUtils {

    public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
    public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;

    /**
     * 获取短时间格式
     *
     * @return
     */
    public static String getShortTime(long millis) {
        Date date = new Date(millis);
        Date curDate = new Date();

        String str = "";
        long durTime = curDate.getTime() - date.getTime();

        int dayStatus = calculateDayStatus(date, new Date());

        if (durTime <= 1 * ONE_MINUTE_MILLIONS) {
            str = "刚刚";
        } else if (durTime < ONE_HOUR_MILLIONS) {
            str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
        } else if (dayStatus == 0) {
            str = durTime / ONE_HOUR_MILLIONS + "小时前";
        } else if (dayStatus == -1) {
            str = "昨天" + DateFormat.format("HH:mm", date);
        } else if (isSameYear(date, curDate) && dayStatus < -1) {
            str = DateFormat.format("MM-dd", date).toString();
        } else {
            str = DateFormat.format("yyyy-MM", date).toString();
        }
        return str;
    }

    /**
     * 获取短时间格式
     *
     * @return
     */
    public static String getShortTime(String millis) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            currentTime = formatter.parse(millis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date curDate = new Date();

        String str = "";
        long durTime = curDate.getTime() - currentTime.getTime();

        int dayStatus = calculateDayStatus(currentTime, new Date());

        if (durTime <= 1 * ONE_MINUTE_MILLIONS) {
            str = "刚刚";
        } else if (durTime < ONE_HOUR_MILLIONS) {
            str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
        } else if (dayStatus == 0) {
            str = durTime / ONE_HOUR_MILLIONS + "小时前";
        } else if (dayStatus == -1) {
            str = "昨天" + DateFormat.format("HH:mm", currentTime);
        } else if (isSameYear(currentTime, curDate) && dayStatus < -1) {
            str = DateFormat.format("MM-dd", currentTime).toString();
        } else {
            str = DateFormat.format("yyyy-MM", currentTime).toString();
        }
        return str;
    }

    /**
     * 获取短时间格式
     *
     * @return
     */
    public static String getShortTime2(long millis) {
        Date date = new Date(millis);
        Date curDate = new Date();

        String str = "";
        int dayStatus = calculateDayStatus(date, new Date());

        if (dayStatus == 0) {
            str = "今天";
        } else if (dayStatus == -1) {
            str = "昨天";
        } else if (isSameYear(date, curDate) && dayStatus < -1) {
            str = DateFormat.format("MM月dd日", date).toString();
        } else {
            str = DateFormat.format("MM月dd日", date).toString();
//            str = DateFormat.format("yy年MM月dd", date).toString();
        }
        return str;
    }

    /**
     * 判断是否是同一年
     *
     * @param targetTime
     * @param compareTime
     * @return
     */
    public static boolean isSameYear(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);

        return tarYear == comYear;
    }

    /**
     * 判断是否处于今天还是昨天，0表示今天，-1表示昨天，小于-1则是昨天以前
     *
     * @param targetTime
     * @param compareTime
     * @return
     */
    public static int calculateDayStatus(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);

        return tarDayOfYear - comDayOfYear;
    }

    /**
     * 将秒数转换成00:00的字符串，如 118秒 -> 01:58
     *
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
