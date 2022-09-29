package com.fudanuniversity.cms.commons.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Xinyue.Tang at 2020-05-19 11:09:20
 */
public final class ValueUtils {

    public static final Integer DEFAULT_INTEGER = 0;

    public static BigDecimal toBigDecimal(final String str) {
        return toBigDecimal(str, BigDecimal.ZERO);
    }

    public static BigDecimal toBigDecimal(final String str, final BigDecimal defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return new BigDecimal(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static Integer toInteger(final String str) {
        return toInteger(str, 0);
    }

    public static Integer toInteger(final String str, final Integer defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static Integer defaultInteger(Integer value) {
        return defaultInteger(value, DEFAULT_INTEGER);
    }

    public static Integer defaultInteger(Integer value, Integer defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static <T> Integer integerOf(T obj, Function<T, Integer> function) {
        if (obj == null) {
            return null;
        }
        return function.apply(obj);
    }

    public static Long toLong(final String str) {
        return toLong(str, 0L);
    }

    public static Long toLong(final String str, final Long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static <T> T defaultObject(Object value) {
        return defaultObject(value, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T defaultObject(Object value, T defaultValue) {
        return value == null ? defaultValue : (T) value;
    }

    public static final Long DEFAULT_LONG = 0L;

    public static Long defaultLong(Long value) {
        return defaultLong(value, DEFAULT_LONG);
    }

    public static Long defaultLong(Long value, Long defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static Long defaultLong(Number value, Long defaultValue) {
        return value == null ? defaultValue : value.longValue();
    }

    public static <T> Long longOf(T obj, Function<T, Long> function) {
        if (obj == null) {
            return null;
        }
        return function.apply(obj);
    }

    public static final String DEFAULT_STRING = null;

    public static String defaultString(Object value) {
        return defaultString(value, DEFAULT_STRING);
    }

    public static String defaultString(Object value, String defaultValue) {
        return value == null ? defaultValue : value.toString();
    }

    public static String defaultString(String value) {
        return defaultString(value, DEFAULT_STRING);
    }

    public static String defaultString(String value, String defaultValue) {
        return value == null ? defaultValue : value;
    }

    @SuppressWarnings("rawtypes")
    public static final List DEFAULT_LIST = Collections.emptyList();

    @SuppressWarnings("unchecked")
    public static <T> List<T> defaultList(List<T> value) {
        return defaultList(value, DEFAULT_LIST);
    }

    public static <T> List<T> defaultList(List<T> value, List<T> defaultValue) {
        return value == null ? defaultValue : value;
    }

    @SuppressWarnings("rawtypes")
    public static final Map DEFAULT_MAP = Collections.emptyMap();

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> defaultMap(Map<K, V> value) {
        return defaultMap(value, DEFAULT_MAP);
    }

    public static <K, V> Map<K, V> defaultMap(Map<K, V> value, Map<K, V> defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static boolean isLongId(Long id) {
        return id != null && id > 0L;
    }
}
