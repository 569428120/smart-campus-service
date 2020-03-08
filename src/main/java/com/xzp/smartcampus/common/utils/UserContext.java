package com.xzp.smartcampus.common.utils;

import com.xzp.smartcampus.portal.vo.LoginUserInfo;

/**
 * 当前登录用户
 */
public class UserContext {
    private final static ThreadLocal<LoginUserInfo> local = new ThreadLocal<>();

    /**
     * 设置当前登录用户
     *
     * @param loginUserInfo loginUserInfo
     */
    public static void setLoginUserInfo(LoginUserInfo loginUserInfo) {
        local.set(loginUserInfo);
    }

    /**
     * 获取当前用户
     *
     * @return LoginUserInfo
     */
    public static LoginUserInfo getLoginUser() {
        return new LoginUserInfo();
        // TODO  方便测试先注释
        //return local.get();
    }
}
