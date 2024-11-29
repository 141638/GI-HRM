package com.gi.hrm.utils;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class DateTimeUtils {

    /**
     * Get current date in java.util.Date
     *
     * @return current date
     */
    public static Date currentDateTime() {
        Instant instant = Instant.now();
        return Date.from(instant);
    }

    /**
     * Get current date in java.sql.Timestamp
     *
     * @return current date
     */
    public static Timestamp currentTimestamp() {
        Instant instant = Instant.now();
        return Timestamp.from(instant);
    }

    /**
     * Convert string to date
     *
     * @param date string value
     * @param format current date string format
     * @return converted date
     */
    public static Date stringToDate(String date, String format) {
        if (ValidationUtils.isNullOrEmpty(date)) {
            return null;
        }
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(
                    date.replace("\u202F", " ")
                            .replace("\u00a0", " ")
                            .replace("&" + "nbsp;", " ")
                            .replaceAll("�+", " "));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert string to timestamp
     *
     * @param date string value
     * @param format current date string format
     * @return converted timestamp
     */
    public static Timestamp convertStringToTimestamp(String date, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert date to string
     *
     * @param date date value
     * @param format string format to convert into
     * @return converted Date string
     */
    public static String dateToString(Date date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert date to string
     *
     * @param localDate date value in java.time.LocalDate
     * @param format string format to convert into
     * @return converted Date string
     */
    public static String dateToString(LocalDate localDate, String format) {
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format);
            return dateFormat.format(localDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert date to string
     *
     * @param localDateTime date value in java.time.LocalDateTime
     * @param format string format to convert into
     * @return converted Date string
     */
    public static String dateToString(LocalDateTime localDateTime, String format) {
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format);
            return dateFormat.format(localDateTime);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert timestamp to string
     *
     * @param timestamp timestamp value
     * @param format string format to convert into
     * @return converted timestamp string
     */
    public static String convertTimestampToString(Timestamp timestamp, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return dateFormat.format(timestamp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Set all date's time to 0(hours, minutes, seconds and milliseconds)
     *
     * @param date date to be set
     * @return set date
     */
    public static Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Return current system datetime in YYYYMMDD format
     *
     * @return system datetime in YYYYMMDD format
     */
    public static String nowBasicISOFormat() {
        return DateTimeUtils.dateToString(new Date(), Pattern.YYYYMMDD);
    }

    /**
     * Date pattern utility class
     */
    @UtilityClass
    public static class Pattern {
        /** date pattern: MM/dd/yyyy */
        public static final String MM_DD_YYYY = "MM/dd/yyyy";
        /** date pattern: dd/MM/yyyy */
        public static final String DD_MM_YYYY = "dd/MM/yyyy";
        /** date pattern: yyyy-MM-dd */
        public static final String YYYY_MM_DD = "yyyy-MM-dd";
        /** date pattern: yyyy-MM */
        public static final String YYYY_MM = "yyyy-MM";
        /** date pattern: dd/MM/yyyy HH:mm:ss */
        public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
        /** date pattern: yyyy-MM-dd HH:mm:ss */
        public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        /** date pattern: HH:mm dd/MM */
        public static final String HH_MM_DD_MM = "HH:mm dd/MM";
        /** date pattern: yyyyMMddHH */
        public static final String YYYYMMDDHH = "yyyyMMddHH";
        /** date pattern: yyyyMMdd */
        public static final String YYYYMMDD = "yyyyMMdd";
    }

}
