package com.aq.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
public class DateUtil {

    public static String getDateby(long timestamp,String dateformat){
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat(dateformat);
        String formatdate = dateFormat.format(date);
        return formatdate;
    }


    public static long getDatebyConditon(long timestamp, String dateformat) throws ParseException {
        Date datetemp = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat(dateformat);
        String formatdate = dateFormat.format(datetemp);
        Date date = dateFormat.parse(formatdate);
        return date.getTime();
    }
}
