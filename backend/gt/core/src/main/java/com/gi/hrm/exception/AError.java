package com.gi.hrm.exception;

import com.gi.hrm.utils.ValidationUtils;
import lombok.experimental.UtilityClass;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class AError {

    public static final AErrorDetail ACCESS_DENIED = new AErrorDetail("ACCESS_DENIED", "Access is denied.");
    public static final AErrorDetail UNEXPECTED_ERROR = new AErrorDetail("UNEXPECTED_ERROR", "Unexpected error。");
    public static final AErrorDetail METHOD_NOT_SUPPORTED = new AErrorDetail("METHOD_NOT_SUPPORTED", "Not supported。");

    /**
     * Get all auto errors
     *
     * @return all auto errors
     */
    public static List<AErrorDetail> all() {
        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(AError.class).getPropertyDescriptors();
            return Arrays.stream(props)
                    .filter(prop -> prop.getPropertyType() == AErrorDetail.class)
                    .map(prop -> (AErrorDetail) prop.getValue(null))
                    .filter(ValidationUtils::nonNullOrEmpty)
                    .toList();
        } catch (IntrospectionException e) {
            throw new InternalError("Failed to retrieve bean info: " + AError.class.getName(), e);
        }
    }
}
