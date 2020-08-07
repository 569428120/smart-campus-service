package com.xzp.smartcampus.common.utils;

import com.xzp.smartcampus.portal.vo.LoginUserInfo;

/**
 * 当前登录用户
 */
public class UserContext {

    private final static ThreadLocal<LoginUserInfo> USER_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前登录用户
     *
     * @param loginUserInfo loginUserInfo
     */
    public static void setLoginUserInfo(LoginUserInfo loginUserInfo) {
        USER_LOCAL.remove();
        USER_LOCAL.set(loginUserInfo);
    }

    /**
     * 获取当前用户
     *
     * @return LoginUserInfo
     */
    public static LoginUserInfo getLoginUser() {
        return USER_LOCAL.get();
    }
}
