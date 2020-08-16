package com.xzp.smartcampus.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;


public class DataUtil {

    /**
     * treePath  截取的长度
     *
     * @param treePath treePath
     * @return int
     */
    public static int getTreePathNumber(String treePath) {
        if (StringUtils.isBlank(treePath)) {
            return 0;
        }
        return treePath.split(Constant.TREE_SEPARATOR).length;
    }

    /**
     * 将字符串转换为列表
     *
     * @param str       str
     * @param separator separator
     * @return separator
     */
    public static List<String> strToList(String str, String separator) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        return Arrays.asList(str.split(separator));
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj 对象
     * @return map
     * @throws IllegalAccessException aa
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            map.put(fieldName, field.get(obj));
        }
        return map;
    }
}
