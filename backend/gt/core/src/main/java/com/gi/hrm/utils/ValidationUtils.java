package com.gi.hrm.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@UtilityClass
public class ValidationUtils {
    /**
     * Check if the object is null or empty
     *
     * @param value object value to check
     * @return true if the object is null or empty
     */
    public static boolean isNullOrEmpty(Object value) {
        return Objects.isNull(value) || ObjectUtils.isEmpty(value)
                || (value instanceof String valueStr && !StringUtils.hasText(valueStr));
    }

    /**
     * Check if the object is not null or empty
     *
     * @param value object value to check
     * @return true if the object is not null or empty
     */
    public static boolean nonNullOrEmpty(Object value) {
        return !isNullOrEmpty(value);
    }

    /**
     * Validate the object value, if null or empty, return the error message
     *
     * @param value object value
     * @param message error message to return if the object is null or empty
     * @return error message/null
     */
    public static String validNullOrEmpty(Object value, String message) {
        if (isNullOrEmpty(value)) {
            return message;
        } else {
            return null;
        }
    }

    /**
     * Aggregate all validate function into one single function, return a list of error messages
     *
     * @param toBeThrown if true, throw immediately instead of returning list of error
     * @param checkFunctions an array contains function that validates value and has its return object is error
     *         message
     * @return a list of error message if there is any error in the functions returns
     */
    @SafeVarargs
    public static List<String> verify(Boolean toBeThrown, Supplier<String>... checkFunctions) {
        List<String> errorMessage = new ArrayList<>();
        for (var checkFunction : checkFunctions) {
            errorMessage.add(checkFunction.get());
        }
        errorMessage = errorMessage.stream().filter(ValidationUtils::nonNullOrEmpty).toList();
        if (Boolean.TRUE.equals(toBeThrown) && ValidationUtils.nonNullOrEmpty(errorMessage)) {
            throw new IllegalArgumentException(String.join(",", errorMessage));
        } else {
            return errorMessage;
        }
    }
}
