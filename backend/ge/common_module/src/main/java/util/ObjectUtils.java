package util;

import org.springframework.beans.BeanUtils;

public class ObjectUtils {
	public static <T> T clone(T object) {
		@SuppressWarnings("unchecked")
		T toCloneObject = (T) object.getClass().getDeclaredConstructors().clone();
		BeanUtils.copyProperties(object, toCloneObject);
		return toCloneObject;
	}
}
