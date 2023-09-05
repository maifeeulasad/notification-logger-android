package com.mua.roti.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:ms";

    public static java.util.Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    public static java.sql.Date getCurrentSqlTime() {
        return new java.sql.Date(new java.util.Date().getTime());
    }

    public static String getCurrentTimeString(String format) {
        return new SimpleDateFormat(format).format(Util.getCurrentTime());
    }

    public static String getCurrentTimeString() {
        return getCurrentTimeString(DATE_FORMAT);
    }

    public static String getTimeString(java.sql.Date time) {
        return new SimpleDateFormat(DATE_FORMAT).format(time);
    }
}
