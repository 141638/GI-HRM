package com.gi.hrm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.gi.hrm.utils.DateTimeUtils.nowBasicISOFormat;

@Slf4j
@UtilityClass
public class StringUtils {
    /** default array string delimiter */
    public static final String ARRAY_DELIMITER = ",";

    /**
     * Convert list of object into string with default delimiter(should apply for primitive type only)
     *
     * @param list list of object
     * @param <T> generic type of object inside list
     * @return list after become string
     */
    public static <T> String listToString(List<T> list) {
        return org.springframework.util.StringUtils.collectionToDelimitedString(list, ARRAY_DELIMITER);
    }

    /**
     * Safely convert a nullable object to string
     *
     * @param obj nullable object
     * @return object string, or null if its does not exist
     */
    public static String toStringNullSafe(Object obj) {
        return ValidationUtils.isNullOrEmpty(obj) ? null : obj.toString();
    }

    /**
     * Convert a string into list of object, with the default delimiter of the utils file
     *
     * @param str list in String format
     * @param clazz type of list item to convert to
     * @param <T> generic type for the item inside the list
     * @return list of object
     */
    public static <T> List<T> stringToList(String str, Class<T> clazz) {
        return stringToList(str, clazz, ARRAY_DELIMITER);
    }

    /**
     * Convert a string into list of object
     *
     * @param str list in String format
     * @param clazz type of list item to convert to
     * @param delimiter list delimiter in the string
     * @param <T> generic type for the item inside the list
     * @return list of object
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> stringToList(String str, Class<T> clazz, String delimiter) {
        if (ValidationUtils.isNullOrEmpty(str)) {
            return new ArrayList<>();
        }
        final ObjectMapper mapper = new ObjectMapper();
        return (List<T>) Stream.of(str.split(delimiter)).map(item -> {
            try {
                if (clazz == String.class) {
                    return item;
                } else if (clazz.isAssignableFrom(Number.class)) {
                    return item;
                } else {
                    return mapper.readValue(item, clazz);
                }
            } catch (JsonProcessingException jsonError) {
                log.error("Cannot convert string to list", jsonError);
            }
            return null;
        }).toList();
    }

    /**
     * Convert a string from camel case format to snake case format
     *
     * @param camel string in camel case
     * @return string in snake case
     */
    public static String camelToSnake(String camel) {
        if (ValidationUtils.isNullOrEmpty(camel)) {
            return camel;
        }

        StringBuilder snakeCaseStr = new StringBuilder();
        for (int i = 0; i < camel.length(); i++) {
            char ch = camel.charAt(i);
            // Check if the character is uppercase
            if (Character.isUpperCase(ch)) {
                // Append an underscore before the uppercase letter, except at the beginning
                if (i > 0) {
                    snakeCaseStr.append('_');
                }
                snakeCaseStr.append(Character.toLowerCase(ch));
            } else {
                snakeCaseStr.append(ch);
            }
        }

        return snakeCaseStr.toString();
    }

    /**
     * Generate a filename combined with UUID and current date
     *
     * @param filename original filename
     * @return generated filename
     */
    public static String generateFileName(String filename) {
        return filename + "-" + UUID.randomUUID() + "-" + nowBasicISOFormat();
    }

    /**
     * Function handles read parse a string list from a string value
     *
     * @param value string value
     * @return string list parsed
     */
    public static List<String> getListString(String value) {
        return StringUtils.stringToList(value, String.class).stream().map(String::strip).toList();
    }
}
