package com.gi.hrm.utils;

import com.gi.hrm.exception.BadRequestException;
import com.google.gson.Gson;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@UtilityClass
public class ObjectUtils {
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
     * Deep copy a map/object source into the target object
     *
     * @param source a map or bean object to be copied
     * @param target the target object to copy into
     * @param log slf4j logger
     */
    @SuppressWarnings("unchecked")
    public static void deepCopyIgnoreNulls(Object source, Object target, Logger log) {
        if (Map.class.isAssignableFrom(source.getClass())) {
            Map<String, String> sourceAsMap = (Map<String, String>) source;
            sourceAsMap.forEach((key, value) -> {
                String updateValue = value;
                try {
                    // to-be-updated property's type
                    Class<?> propertyType = org.apache.commons.beanutils.PropertyUtils.getPropertyType(target, key);
                    // handle floating-point number for int and long class

                    if (List.of(Integer.class, Long.class).contains(propertyType) && updateValue.contains(".")) {
                        updateValue = updateValue.split("\\.")[0];
                    }
                    // set value
                    org.apache.commons.beanutils.BeanUtils.setProperty(target, key, updateValue);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    String errorMessage = String.format(
                            "Exception occurred when trying to map %s=%s into target object", key, value);
                    log.error(errorMessage, e);
                }
            });
        } else {
            BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        }
    }

    /**
     * Deep clone an object
     *
     * @param object object instance
     * @param clazz object class
     * @param log slf4j logger
     * @param <T> object class's generic type
     * @return newly cloned object
     */
    public static <T> T deepClone(T object, Class<T> clazz, Logger log) {
        try {
            T newObject = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(object, newObject, getNullPropertyNames(object));
            return newObject;
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            log.error("Exception occurred in CommonUtils.deepCopy", e);
            throw new IllegalStateException("Failed to deepClone object");
        }
    }

    /**
     * Get all fields properties from an object
     *
     * @param beanClass class object to get properties from
     * @param <T> generic type for object class
     * @param log logger
     * @return {@link PropertyDescriptor} array
     */
    public static <T> PropertyDescriptor[] getBeanProperties(Class<T> beanClass, Logger log) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
        } catch (IntrospectionException e) {
            log.error("Error getting bean info");
            return new PropertyDescriptor[0];
        }
        return beanInfo.getPropertyDescriptors();
    }

    /**
     * Cast an object to a specific return type
     *
     * @param obj object to be cast
     * @param <T> generic type to cast into
     * @return cast object
     */
    public static <T> T cast(Object obj) {
        return cast(obj, null);
    }

    /**
     * Cast an object to a specific return type
     *
     * @param obj object to be cast
     * @param nullErrorMessage if null error message is defined, throw the error when the object is null
     * @param <T> generic type to cast into
     * @return cast object
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj, String nullErrorMessage) {
        if (ValidationUtils.isNullOrEmpty(obj)) {
            if (ValidationUtils.isNullOrEmpty(nullErrorMessage)) {
                return null;
            } else {
                throw new BadRequestException(nullErrorMessage);
            }
        } else {
            return (T) obj;
        }
    }

    /**
     * Convert the object to JSON string
     *
     * @param obj object
     * @return JSON string
     */
    public static String toJSON(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * Convert the JSON string to object
     *
     * @param jsonStr JSON string
     * @param type type to convert to
     * @param <T> generic type for the type convert
     * @return object
     */
    public static <T> T parseJSON(String jsonStr, Type type) {
        if (ValidationUtils.isNullOrEmpty(jsonStr)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, type);
    }
}
