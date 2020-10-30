package com.xzp.smartcampus.portal.service;


import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    Map<String, Object> userLogin(String userName, String password, String code);

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return LoginUserInfo
     */
    LoginUserInfo getLoginUserByToken(String token);

    /**
     * 手机号码登录
     *
     * @param mobileNumber     手机号码
     * @param verificationCode 验证码
     * @return 返回
     */
    Map<String, Object> mobileLogin(String mobileNumber, String verificationCode);

    /**
     * 创建登录对象
     *
     * @param staffModel 用户
     * @return LoginUserInfo
     */
    LoginUserInfo createLoginUser(StaffModel staffModel);
}
