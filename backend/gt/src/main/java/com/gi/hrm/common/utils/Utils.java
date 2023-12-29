package com.gi.hrm.common.utils;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	public static String regexFilter(String regex, String name) {
		var pattern = Pattern.compile(regex);
		var matcher = pattern.matcher(name);
		try {
			return matcher.results().map(Object::toString).collect(Collectors.joining());
		} catch (Exception e) {
			log.error("regexFilter: {}", e);
			return null;
		}
	}
}
