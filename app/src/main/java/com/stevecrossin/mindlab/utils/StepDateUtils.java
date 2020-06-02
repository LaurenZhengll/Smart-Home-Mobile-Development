package com.stevecrossin.mindlab.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: StepDateUtils
 * @Description: Time tools (time format conversion tools)
 * @Author: yanxu5
 * @Date: 2019/8/19
 */
class StepDateUtils {

    private static ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT = new ThreadLocal<>();

    private static SimpleDateFormat getDateFormat() {
        SimpleDateFormat df = SIMPLE_DATE_FORMAT.get();
        if (df == null) {
            df = new SimpleDateFormat();
            SIMPLE_DATE_FORMAT.set(df);
        }
        return df;
    }

    /**
     * Returns the current time in a certain format
     *
     * @param pattern "yyyy-MM-dd HH:mm:ss E"
     * @return String
     */
    public static String getCurrentDate(String pattern) {
        getDateFormat().applyPattern(pattern);
        Date date = new Date(System.currentTimeMillis());
        return getDateFormat().format(date);
    }

    private static long getDateMillis(String dateString, String pattern) {
        long millionSeconds = 0;
        getDateFormat().applyPattern(pattern);
        try {
            millionSeconds = getDateFormat().parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }// millisecond
        return millionSeconds;
    }

    /**
     * Format input millis
     *
     * @param millis  millis
     * @param pattern yyyy-MM-dd HH:mm:ss E
     * @return String
     */
    private static String dateFormat(long millis, String pattern) {
        getDateFormat().applyPattern(pattern);
        Date date = new Date(millis);
        return getDateFormat().format(date);
    }

    /**
     * Convert the original old format of dateString to new format
     *
     * @param dateString dateString
     * @param oldPattern yyyy-MM-dd HH:mm:ss E
     * @param newPattern newPattern
     * @return oldPattern is different from dateString and returns dateString directly
     */
    private static String dateFormat(String dateString, String oldPattern, String newPattern) {
        long millis = getDateMillis(dateString, oldPattern);
        if (0 == millis) {
            return dateString;
        }
        return dateFormat(millis, newPattern);
    }

}
