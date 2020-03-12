package com.xzp.smartcampus.portal.service;


import com.xzp.smartcampus.portal.vo.LoginUserInfo;

import java.util.Map;

/**
 * 用户认证接口
 */
public interface IAuthService {

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @param code     验证码
     * @return String
     */
    Map<String, String> userLogin(String userName, String password, String code);

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return LoginUserInfo
     */
    LoginUserInfo getLoginUserByToken(String token);
}
