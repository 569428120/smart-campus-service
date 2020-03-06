package com.xzp.smartcampus.common.utils;

import java.util.UUID;

public class SqlUtil {

    /**
     * 获取uuid
     *
     * @return
     */
    public static String getUUId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
