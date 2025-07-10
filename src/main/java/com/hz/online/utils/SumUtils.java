package com.hz.online.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumUtils {

    public static Map<String, Object> calculateSums(List<?> dataList, List<String> sumFields) {
        Map<String, Object> result = new HashMap<>();
        if (dataList == null || dataList.isEmpty() || sumFields == null || sumFields.isEmpty()) {
            return result;
        }

        for (String fieldName : sumFields) {
            try {
                Object total = null;
                for (Object item : dataList) {
                    Field field = getDeclaredField(item.getClass(), fieldName);
                    if (field == null) continue;

                    field.setAccessible(true);
                    Object value = field.get(item);
                    if (value == null) continue;

                    if (value instanceof BigDecimal) {
                        total = total == null ? value : ((BigDecimal) total).add((BigDecimal) value);
                    } else if (value instanceof Integer) {
                        total = total == null ? value : (Integer) total + (Integer) value;
                    } else if (value instanceof Long) {
                        total = total == null ? value : (Long) total + (Long) value;
                    } else if (value instanceof Double) {
                        total = total == null ? value : (Double) total + (Double) value;
                    }
                    // 其他类型暂不支持
                }
                result.put(fieldName, total == null ? 0 : total);
            } catch (Exception e) {
                result.put(fieldName, 0); // 避免异常中断
            }
        }

        return result;
    }

    private static Field getDeclaredField(Class<?> clazz, String fieldName) {
        while (clazz != null && clazz != Object.class) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}

