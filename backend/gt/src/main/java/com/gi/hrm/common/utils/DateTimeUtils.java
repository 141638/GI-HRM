package com.gi.hrm.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.gi.hrm.exception.RecordNotFoundException;

public class DateTimeUtils {
	public static final String TIME_ZONE_VI = "Asia/Ho_Chi_Minh";
	public static final String DATE_PATTERN_YYYY_MM = "yyyy-MM";
	public static final String DATE_PATTERN_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_PATTERN_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN_HH_MM_DD_MM = "HH:mm dd/MM";

	private DateTimeUtils() {
	}

	public static Date currentDateTime() {
		Instant instant = Instant.now();
		return Date.from(instant);
	}

	public static Timestamp currentTimestamp() {
		Instant instant = Instant.now();
		return Timestamp.from(instant);
	}

	public static Date stringToDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(Date date, String format) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date formatDate(String date, String pattern, String key) {
		if (StringUtils.hasText(date) || StringUtils.hasText(pattern)) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			format.setLenient(false);
			return format.parse(date);
		} catch (ParseException e) {
			throw new RecordNotFoundException(e.getMessage());
		}
	}

	public static Timestamp timestampBefore() {
		Instant instant = Instant.now();
		Instant newInstant = instant.plus(Duration.ofMinutes(1));
		return Timestamp.from(newInstant);
	}

	public static Timestamp stringToTimestamp(String bookingTime, String datePattern) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
			Date parsedDate = dateFormat.parse(bookingTime);
			return new java.sql.Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			return null;
		}
	}
}
