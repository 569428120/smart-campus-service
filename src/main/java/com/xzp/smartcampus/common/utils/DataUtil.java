package com.xzp.smartcampus.common.utils;

import org.apache.commons.lang3.StringUtils;

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
}
