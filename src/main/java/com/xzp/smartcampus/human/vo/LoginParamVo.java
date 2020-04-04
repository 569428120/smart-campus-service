package com.xzp.smartcampus.human.vo;

import lombok.Data;

/**
 * 登录参数
 */
@Data
public class LoginParamVo {

    /**
     * 登录名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录类型
     */
    private String type;
}
