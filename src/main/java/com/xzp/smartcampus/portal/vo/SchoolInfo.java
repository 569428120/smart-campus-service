package com.xzp.smartcampus.portal.vo;

import lombok.Data;

/**
 * @author SGS
 */
@Data
public class SchoolInfo {

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学校类型
     */
    private String schoolType;

    /**
     * 联系方式
     */
    private String contact;
}
