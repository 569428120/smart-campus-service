package com.xzp.smartcampus.mobileapi.param;

import lombok.Data;

/**
 * @author SGS
 */
@Data
public class CardQueryParam {

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 用户编码
     */
    private String userNumber;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户名称
     */
    private String userName;
}
