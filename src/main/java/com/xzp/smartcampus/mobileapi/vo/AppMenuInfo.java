package com.xzp.smartcampus.mobileapi.vo;

import lombok.Data;

/**
 * @author SGS
 */
@Data
public class AppMenuInfo {

    /**
     * 操作id
     */
    private String id;

    /**
     * 操作名称
     */
    private String operateName;

    /**
     * 操作编码
     */
    private String operateCode;

    /**
     * 描述
     */
    private String description;
}
