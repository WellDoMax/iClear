package com.laowulao.noads.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");

    public static String getCurrentDateStr(){
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        return format.format(date);
    }

    private static long date2Millis(String date){
        if(TextUtils.isEmpty(date)){
            return 0;
        }

        try {
            Date d = format.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getMomentAgo(String date){
        long old = date2Millis(date);
        if(old == 0){
            return "0";
        }
        long now = System.currentTimeMillis();
        long moment = now - old;

        int second = (int) (moment/1000);
        int min = second/60;
        int hour = min/60;
        int day = hour/24;
        if(day == 0){
            if(hour == 0){
                if(min == 0){
                    return second + "秒前";
                } else {
                    return min + "分钟前";
                }
            } else {
                return hour + "小时前";
            }
        } else {
            if(day < 10){
                return day + "天前";
            } else {
                return date;
            }

        }

    }

    public static String getMoment(String date){
        long old = date2Millis(date);
        if(old == 0){
            return "0";
        }
        long now = System.currentTimeMillis();
        long moment = now - old;

        int second = (int) (moment/1000);
        int min = second/60;
        int hour = min/60;
        int day = hour/24;
        if(day == 0){
            if(hour == 0){
                if(min == 0){
                    return second + "秒前";
                } else {
                    return min + "分钟前";
                }
            } else {
                return hour + "小时前";
            }
        } else {
            if(day < 10){
                return day + "天前";
            } else {
                return date;
            }

        }

    }


}
