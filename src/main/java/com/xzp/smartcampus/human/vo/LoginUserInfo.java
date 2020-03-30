package com.xzp.smartcampus.human.vo;

import lombok.Data;

import java.util.Map;

/**
 * 登录用户数据
 */
@Data
public class LoginUserInfo {

    /**
     * 当前用户的id
     */
    private String userId;

    /**
     * 当前用户类型
     */
    private String userType;

    /**
     * 教育局map
     */
    private Map<String, String> regionIdToName;

    /**
     * 学校id
     */
    private Map<String, String> schoolIdToName;
}
