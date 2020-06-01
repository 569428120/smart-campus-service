package com.xzp.smartcampus.portal.vo;

import lombok.Data;

import java.util.List;
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
     * 区域id
     */
    private String regionId;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 教育局map
     */
    private Map<String, String> regionIdToName;

    /**
     * 学校id
     */
    private Map<String, String> schoolIdToName;
}
