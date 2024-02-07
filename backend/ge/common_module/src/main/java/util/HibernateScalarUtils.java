package util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

public class HibernateScalarUtils {
	@SuppressWarnings("deprecation")
	public static <T> void addScalar(NativeQuery<?> sqlQuery, Class<T> clazz) {
		nullPointerException(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			addScalarTypeLong(field, sqlQuery);
			addScalarTypeInteger(field, sqlQuery);
			addScalarTypeCharacter(field, sqlQuery);
			addScalarTypeShort(field, sqlQuery);
			addScalarTypeDouble(field, sqlQuery);
			addScalarTypeFloat(field, sqlQuery);
			addScalarTypeBoolean(field, sqlQuery);
			addScalarTypeString(field, sqlQuery);
			addScalarTypeDate(field, sqlQuery);
			addScalarTimestamp(field, sqlQuery);
		}
		sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
	}

	private static <T> void nullPointerException(Class<T> clazz) {
		if (clazz == null) {
			throw new NullPointerException("[clazz] could not be null!");
		}
	}

	private static void addScalarTypeLong(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == long.class || field.getType() == Long.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.LONG);
		}
	}

	private static void addScalarTypeInteger(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == int.class || field.getType() == Integer.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.INTEGER);
		}
	}

	private static void addScalarTypeCharacter(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == char.class || field.getType() == Character.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.CHARACTER);
		}
	}

	private static void addScalarTypeShort(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == short.class || field.getType() == Short.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.SHORT);
		}
	}

	private static void addScalarTypeDouble(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == double.class || field.getType() == Double.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.DOUBLE);
		}
	}

	private static void addScalarTypeFloat(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == float.class || field.getType() == Float.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.FLOAT);
		}
	}

	private static void addScalarTypeBoolean(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == boolean.class || field.getType() == Boolean.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.BOOLEAN);
		}
	}

	private static void addScalarTypeString(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == String.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.STRING);
		}
	}

	private static void addScalarTypeDate(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == Date.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.DATE);
		}
	}

	private static void addScalarTimestamp(Field field, NativeQuery<?> sqlQuery) {
		if (field.getType() == Timestamp.class) {
			sqlQuery.addScalar(field.getName(), StandardBasicTypes.TIMESTAMP);
		}
	}
}
