package org.example.back.demos.util;

import java.lang.reflect.Field;

/**
 * @author ljn
 * @Description: 类型元素非空判断
 * @date 2024/3/2711:27
 */

public class ReflectionUtils {
    public static <T> void checkNonNullFields(T obj, Class<T> clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object fieldValue = field.get(obj);

            if (fieldValue == null) {
                throw new IllegalArgumentException("Field " + field.getName() + " in " + clazz.getSimpleName() + " cannot be null");
            } else check(field, fieldValue, clazz.getSimpleName(), clazz);
        }
    }

    public static <T> void checkFieldsNoEmpty(T obj, Class<T> clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object fieldValue = field.get(obj);

            if (fieldValue != null) {
                check(field, fieldValue, clazz.getSimpleName(), clazz);
            }
        }
    }

    private static <T> void check(Field field, Object fieldValue, String simpleName, Class<T> clazz) {
        if (field.getType() == String.class && ((String) fieldValue).isEmpty()) {
           throw new IllegalArgumentException("Field " + field.getName() + " in " + simpleName + " cannot be an empty string");
       } else if (fieldValue instanceof Number && isIntegerType(field.getType()) && ((Number) fieldValue).intValue() <= 0) {
           throw new IllegalArgumentException("Field " + field.getName() + " in " + simpleName + " must be greater than 0");
       }
    }

    private static boolean isIntegerType(Class<?> type) {
        return int.class.equals(type) || Integer.class.equals(type);
    }

    public static void checkIntMoreZero(Integer ...data) throws Exception {
        for (Integer i : data) {
            if (i <= 0) {
                throw new Exception("Field " + i + " in " + " cannot be less than 0");
            }
        }
    }
}
