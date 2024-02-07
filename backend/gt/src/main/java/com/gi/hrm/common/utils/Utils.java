package com.gi.hrm.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            log.error("regex filter: {}", e);
            return null;
        }
    }

    public static <T> T copy(Object source, Class<T> clazz) {
        final BeanWrapper wrapSource = new BeanWrapperImpl(source);
        var ignoreNulls = Arrays.stream(wrapSource.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> Objects.isNull(wrapSource.getPropertyValue(name)))
                .toArray(String[]::new);

        try {
            var target = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target, ignoreNulls);

            return target;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            log.error("Error occurred when instantiating an object from Class<T>");
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
