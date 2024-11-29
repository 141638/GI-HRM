package com.gi.hrm.utils;

import com.gi.hrm.query.query.CollectionFieldMetadata;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;


@UtilityClass
public class LibUtils {
    /**
     * Convert string to date
     *
     * @param date string value
     * @param format current date string format
     * @return converted date
     */
    public static Date stringToDate(String date, String format) {
        if (Objects.isNull(date)) {
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
     * Get null value fields name from object
     *
     * @param source source object
     * @return field names that have null value
     */
    public static String[] getNullPropertyNames(Object source) {
        // get bean wrapper from source object
        final BeanWrapper src = new BeanWrapperImpl(source);
        // retrieve property descriptors
        var pds = src.getPropertyDescriptors();

        // init result set
        Set<String> emptyNames = new HashSet<>();

        // iterate through properties, add the null field into result set
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        // return result
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * Convert a string from camel case format to snake case format
     *
     * @param camel string in camel case
     * @return string in snake case
     */
    public static String camelToSnake(String camel) {
        if (!StringUtils.hasText(camel)) {
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
     * Parse the bean's {@link PropertyDescriptor} array into field metadata
     *
     * @param properties the bean's {@link PropertyDescriptor} array
     * @return the parsed {@link CollectionFieldMetadata}
     */
    public static List<CollectionFieldMetadata> parseFieldMetadata(PropertyDescriptor[] properties) {
        List<CollectionFieldMetadata> output = new ArrayList<>();

        // convert properties into metadata object
        Arrays.stream(properties).filter(property -> !property.getName().equals("class")).forEach(property ->
                output.add(new CollectionFieldMetadata(
                        property.getName(),
                        camelToSnake(property.getName()),
                        property.getPropertyType().getTypeName(),
                        parseJSTypeName(property.getPropertyType()))));
        return output;
    }

    /**
     * Parse the java class into JS class name
     *
     * @param clazz java class
     * @return JS class name
     */
    public static String parseJSTypeName(Class<?> clazz) {
        if (String.class.isAssignableFrom(clazz)) {
            return "string";
        } else if (Number.class.isAssignableFrom(clazz)
                || clazz.getTypeName().equals("double")
                || clazz.getTypeName().equals("long")
                || clazz.getTypeName().equals("float")
                || clazz.getTypeName().equals("int")) {
            return "number";
        } else if (Date.class.isAssignableFrom(clazz) || Temporal.class.isAssignableFrom(clazz)) {
            return "timestamp";
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            return "boolean";
        } else if (List.class.isAssignableFrom(clazz)) {
            return "array";
        } else {
            return "undefined";
        }
    }
}
