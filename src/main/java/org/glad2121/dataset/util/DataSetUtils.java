package org.glad2121.dataset.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * 
 * @author GLAD!!
 */
public class DataSetUtils {

    public static Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return Integer.class.cast(value);
        }
        if (value instanceof Number) {
            return Number.class.cast(value).intValue();
        }
        return Integer.parseInt(value.toString());
    }

    public static Long toLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return Long.class.cast(value);
        }
        if (value instanceof Number) {
            return Number.class.cast(value).longValue();
        }
        if (value instanceof Date) {
            return Date.class.cast(value).getTime();
        }
        return Long.parseLong(value.toString());
    }

    public static String toString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return String.class.cast(value);
        }
        if (value instanceof Date) {
            return dateToString((Date) value);
        }
        return value.toString();
    }

    public static String toString(Long value, Class<?> type) {
        if (value == null) {
            return null;
        }
        if (type == java.sql.Date.class) {
            return new java.sql.Date(value).toString();
        }
        if (type == java.sql.Time.class) {
            return new java.sql.Time(value).toString();
        }
        if (Date.class.isAssignableFrom(type)) {
            return timestampToString(new java.sql.Timestamp(value));
        }
        return String.valueOf(value);
    }

    static String dateToString(Date value) {
        if (value instanceof java.sql.Date) {
            return value.toString();
        }
        if (value instanceof java.sql.Time) {
            return value.toString();
        }
        if (value instanceof java.sql.Timestamp) {
            return timestampToString((Timestamp) value);
        }
        return timestampToString(new Timestamp(value.getTime()));
    }

    static String timestampToString(Timestamp value) {
        return chomp(value.toString(), " 00:00:00.0");
    }

    static String chomp(String s, String suffix) {
        if (!s.endsWith(suffix)) {
            return s;
        }
        return s.substring(0, s.length() - suffix.length());
    }

    public static String getTestResourcePath(
            Class<?> testClass, String suffix) {
        return testClass.getName().replace('.', '/') + suffix;
    }

    public static String getTestResourcePath(
            Class<?> testClass, String name, String suffix) {
        return getTestResourcePath(testClass, '_' + name + suffix);
    }

}
