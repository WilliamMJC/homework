package com.zt.homework.Utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm" );

    // 将Timestamp类型转换为String类型
    public static String timestamp2String(Timestamp timestamp) {
        Date d1 = new Date(timestamp.getTime());
        return sdf.format(d1);
    }

    // 将String类型转换为Timestamp类型
    public static Timestamp string2Timestamp(String date){
        Date d1 = null;
        try {
            d1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  new Timestamp(d1.getTime());
    }

    // 将TimeStamp类型转换为Date类型
    public static Date timeStamp2Date(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    // 将Date类型转换为TimeStamp类型
    public static Timestamp Date2Timestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    // date转string
    public static String Date2String(Date date) {
        return sdf.format(date);
    }

    // string转date
    public static Date string2Date(String string) {
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 比较两个日期的前后,如果a在前，则返回true
    public static boolean compare(Date d1, Date d2) {
        return (d1.getTime() - d2.getTime()) < 0;
    }
}
