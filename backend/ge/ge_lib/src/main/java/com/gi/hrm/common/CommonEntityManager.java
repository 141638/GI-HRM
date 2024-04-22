package com.gi.hrm.common;

import com.gi.hrm.exception.ObjectFieldsMismatchException;
import com.gi.hrm.service.ResultPageResponseService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;


@ComponentScan(basePackages = "service")
public class CommonEntityManager {
    private static final String QUERY_COUNT = "SELECT COUNT(*)\n";
    protected static final String DATE_SQLFORMAT = "'yyyy-MM-dd'";
    protected static final Map<String, String> paramsMapping = new HashMap<>();

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected ResultPageResponseService pageResponseImpl;

    protected StringBuilder sqlBuilder() {
        return new StringBuilder();
    }

    protected Session session() {
        return entityManager.unwrap(Session.class);
    }

    protected void offSetLimit(Integer currentPage, Integer pageSize, NativeQuery<?> query) {
        int offset = (currentPage - 1) * pageSize;
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
    }

    protected String buildCountSql(String listSql, String QUERY_SELECT, String QUERY_ORDER) {
        String result = listSql.replace(QUERY_SELECT, QUERY_COUNT);
        if (StringUtils.hasText(QUERY_ORDER)) {
            result = result.replace(QUERY_ORDER, "");
        }
        return result;
    }

    protected void setParamsMapping(List<String> listFields, Object requestParams, StringBuilder sqlBuilder) {
        setParamsMapping(listFields, requestParams, sqlBuilder, "");
    }

    protected void setParamsMapping(List<String> listFields, Object requestParams, StringBuilder sqlBuilder,
                                    String... exceptField) {
        Field[] params = requestParams.getClass().getDeclaredFields();
        int fieldsSize = listFields.size();
        int paramsSize = params.length;
        if (fieldsSize != paramsSize) {
            throw new ObjectFieldsMismatchException();
        }
        List<String> exceptFieldName = Arrays.asList(exceptField);
        for (int i = 0; i < fieldsSize; i++) {
            String paramName = params[i].getName();
            if (!exceptFieldName.isEmpty() && exceptFieldName.contains(paramName)) {
                continue;
            }
            Object paramValue = null;
            try {
                params[i].setAccessible(true);
                paramValue = params[i].get(requestParams);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                System.err.println(e.getMessage());
            }
            if (Objects.nonNull(paramValue)) {
                try {
                    List<?> listType = (List<?>) paramValue;
                    if (listType.isEmpty()) {
                        continue;
                    }
                } catch (Exception ignored) {
                }
                paramsMapping.put(paramName, listFields.get(i));
                setParameterNumberOrBoolean(params[i], sqlBuilder);
                setParameterString(params[i], sqlBuilder);
                setParameterList(params[i], sqlBuilder);
                setParameterDate(params[i], sqlBuilder);
                setParameterKeys(params[i], sqlBuilder);
            } else {
                paramsMapping.remove(paramName);
            }
        }
    }

    protected void setQueryParameters(List<NativeQuery<?>> query, Object requestParams) {
        Field[] params = requestParams.getClass().getDeclaredFields();
        for (Field field : params) {
            query.forEach(item -> {
                String paramName = field.getName();
                if (paramsMapping.containsKey(paramName)) {
                    try {
                        field.setAccessible(true);
                        Object paramValue = field.get(requestParams);
                        if (paramValue instanceof String
                                && (paramName.contains("keys") || paramName.contains("Keys"))) {
                            String keys = String.valueOf(paramValue);
                            String[] keyArray = keys.split(",");
                            for (int i = 0; i < keyArray.length; i++) {
                                item.setParameter(paramName + i, '%' + keyArray[i] + '%');
                            }
                        } else {
                            item.setParameter(paramName, updateParamValue(field, paramValue));
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        System.err.println(e.getMessage());
                    }
                }
            });
        }
    }

    private Object updateParamValue(Field field, Object paramValue) {
        if (field.getType() == String.class) {
            return "%" + String.valueOf(paramValue).toLowerCase() + "%";
        }
        if (field.getType() == Date.class) {
            try {
                return new Timestamp(((Date) paramValue).getTime());
            } catch (Exception ignored) {
            }
        }
        return paramValue;
    }

    private void setParameterNumberOrBoolean(Field field, StringBuilder sqlBuilder) {
        if (Number.class.isAssignableFrom(field.getType()) || field.getType() == Boolean.class) {
            String requestName = field.getName();
            String fieldCond = paramsMapping.get(requestName);
            sqlBuilder.append("AND ").append(fieldCond).append(" = ").append(":").append(requestName).append("\n");
        }
    }

    private void setParameterString(Field field, StringBuilder sqlBuilder) {
        if (field.getType() == String.class
                && !(field.getName().contains("keys") || field.getName().contains("Keys"))) {
            String requestName = field.getName();
            String fieldCond = paramsMapping.get(requestName);
            sqlBuilder.append("AND lower(").append(fieldCond).append(") like :").append(requestName).append("\n");
        }
    }

    private void setParameterList(Field field, StringBuilder sqlBuilder) {
        if (field.getType() == List.class) {
            String requestName = field.getName();
            String fieldCond = paramsMapping.get(requestName);
            sqlBuilder.append("AND ").append(fieldCond).append(" in ").append(":").append(requestName).append("\n");
        }
    }

    private void setParameterDate(Field field, StringBuilder sqlBuilder) {
        if (field.getType() == Date.class) {
            String requestName = field.getName();
            String fieldCond = paramsMapping.get(requestName);
            if (requestName.toLowerCase().contains("start")) {
                sqlBuilder.append("AND ").append(fieldCond).append(" <= :").append(requestName).append("\n");
            } else if (requestName.toLowerCase().contains("end")) {

                sqlBuilder.append("AND ").append(fieldCond).append(" >= :").append(requestName).append("\n");
            } else {
                sqlBuilder.append("AND ").append(fieldCond).append(" = :").append(requestName).append("\n");
            }
        }
    }

    private void setParameterKeys(Field field, StringBuilder sqlBuilder) {
        if (field.getType() == String.class && (field.getName().contains("keys") || field.getName().contains("Keys"))) {
            String requestName = field.getName();
            String fieldCond = paramsMapping.get(requestName);
            int keysCount = fieldCond.split(",").length;
            for (int i = 0; i < keysCount; i++) {
                sqlBuilder.append("AND lower(").append(fieldCond).append("\\:\\:text) like :").append(requestName)
                        .append(String.valueOf(i)).append("\n");
            }
        }

    }
}
