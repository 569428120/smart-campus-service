package com.xzp.smartcampus.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
}
