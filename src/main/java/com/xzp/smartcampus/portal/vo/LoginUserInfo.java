package com.xzp.smartcampus.portal.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 登录用户数据
 *
 * @author SGS
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
     * 用户编号
     */
    private String userNumber;

    /**
     * 手机号码
     */
    private String contact;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 如果用户为老师，则显示管理的班级列表，如果是学生则是学生所属的班级
     */
    private List<HisClassInfo> hisClass;

    /**
     * 当前教育局
     */
    private RegionInfo currRegionInfo;

    /**
     * 当前学校
     */
    private SchoolInfo currSchoolInfo;

    /**
     * 可切换的区域列表
     */
    private List<RegionInfo> regionInfoList;

    /**
     * 可切换的学校列表
     */
    private List<SchoolInfo> schoolInfoList;
}
