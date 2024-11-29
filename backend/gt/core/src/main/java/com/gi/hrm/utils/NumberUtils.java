package com.gi.hrm.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtils {
    /**
     * Parse double null-safe
     *
     * @param val nullable string value
     * @return null/double
     */
    public static Double parseDouble(String val) {
        return ValidationUtils.isNullOrEmpty(val) ? null : Double.parseDouble(val);
    }

    /**
     * Parse long from int with null-safe
     *
     * @param val nullable Integer value
     * @return null/long
     */
    public static Long intParseLong(Integer val) {
        return ValidationUtils.isNullOrEmpty(val) ? null : val.longValue();
    }

    /**
     * Parse long from int with null-safe
     *
     * @param val nullable Integer value
     * @return null/long
     */
    public static Float intParseFloat(Integer val) {
        return ValidationUtils.isNullOrEmpty(val) ? null : val.floatValue();
    }

    /**
     * Parse int null-safe
     *
     * @param val nullable string value
     * @return null/int
     */
    public static Integer parseInt(String val) {
        return ValidationUtils.isNullOrEmpty(val) ? null : Double.valueOf(val).intValue();
    }
}
