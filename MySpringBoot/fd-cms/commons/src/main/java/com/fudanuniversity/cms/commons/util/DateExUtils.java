package com.fudanuniversity.cms.commons.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * DateExUtils类命名区别于apache.commons.lang3中的DateUtils
 * <p>
 * Created by Xinyue.Tang at 2020-05-14 15:54:36
 */
public class DateExUtils {

    public static final String MONTH_PATTERN = "yyyy-MM";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_COMPACT_PATTERN = "yyyyMMddHHmmss";

    public static final String DATETIME_MILLI_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private static ThreadLocal<SimpleDateFormat> monthFormatHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(MONTH_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> dateFormatHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> timeFormatHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(TIME_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> datetimeFormatHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATETIME_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> compactDatetimeFormatHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATETIME_COMPACT_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> datetimeMilliFormatHolder = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATETIME_MILLI_PATTERN);
        }
    };

    public static SimpleDateFormat getMonthFormat() {
        return monthFormatHolder.get();
    }

    public static Date parseMonth(String text) {
        try {
            return getMonthFormat().parse(text);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Could not parse month: [" + text + "]", pe);
        }
    }

    public static String formatMonth(Date date) {
        return getMonthFormat().format(date);
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormatHolder.get();
    }

    public static Date parseDate(String text) {
        try {
            return getDateFormat().parse(text);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Could not parse date: [" + text + "]", pe);
        }
    }

    public static String formatDate(Date date) {
        return getDateFormat().format(date);
    }

    public static SimpleDateFormat getTimeFormat() {
        return timeFormatHolder.get();
    }

    public static Date parseTime(String text) {
        try {
            return getTimeFormat().parse(text);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Could not parse time: [" + text + "]", pe);
        }
    }

    public static String formatTime(Date date) {
        return getTimeFormat().format(date);
    }

    public static Date parseCompactDatetime(String text) {
        try {
            return getCompactDatetimeFormat().parse(text);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Could not parse datetime: [" + text + "]", pe);
        }
    }

    public static SimpleDateFormat getCompactDatetimeFormat() {
        return compactDatetimeFormatHolder.get();
    }

    public static String formatCompactDatetime(Date date) {
        return getCompactDatetimeFormat().format(date);
    }


    public static SimpleDateFormat getDatetimeFormat() {
        return datetimeFormatHolder.get();
    }

    public static Date parseDatetime(String text) {
        try {
            return getDatetimeFormat().parse(text);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Could not parse datetime: [" + text + "]", pe);
        }
    }

    public static String formatDatetime(Date date) {
        return getDatetimeFormat().format(date);
    }

    public static SimpleDateFormat getDatetimeMilliFormat() {
        return datetimeMilliFormatHolder.get();
    }

    public static Date parseDatetimeMilli(String text) {
        try {
            return getDatetimeMilliFormat().parse(text);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Could not parse datetime: [" + text + "]", pe);
        }
    }

    public static String formatDatetimeMilli(Date date) {
        return getDatetimeMilliFormat().format(date);
    }

    public static Date eval(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Integer getYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static Integer getYear(Date date, TimeZone zone) {
        if (date == null) {
            return null;
        }
        Calendar calendar;
        if (zone == null) {
            calendar = Calendar.getInstance();
        } else {
            calendar = Calendar.getInstance(zone);
        }
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回date1 -> date2共经历了多少天
     */
    public static long evalCrossDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new NullPointerException();
        }

        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(localDate1, localDate2);
    }

    /**
     * 返回date1 -> date2共经历了多少年
     */
    public static long evalCrossYears(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new NullPointerException();
        }

        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.YEARS.between(localDate1, localDate2);
    }

    /**
     * 动态适配日期转换，目前支持一下几种格式
     * <ul>
     *   <li>"yyyy-MM-dd"</li>
     *   <li>"yyyy-MM-dd HH:mm:ss"</li>
     *   <li>"yyyy-MM-dd HH:mm:ss.SSS"</li>
     * </ul>
     */
    public static Date parseDynamicFormat(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        //根据字符串长度匹配转换时间
        if (StringUtils.isNumeric(source)) {
            long timestamp = Long.parseLong(source);
            return new Date(timestamp);
        } else if (source.length() == DATE_PATTERN.length()) {
            return DateExUtils.parseDate(source);
        } else if (source.length() == DATETIME_PATTERN.length()) {
            return DateExUtils.parseDatetime(source);
        } else if (source.length() == DATETIME_MILLI_PATTERN.length()) {
            return DateExUtils.parseDatetimeMilli(source);
        }

        throw new IllegalArgumentException("cannot parse date: " + source);
    }
}
