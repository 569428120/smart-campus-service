package com.xzp.smartcampus.common.utils;

import com.xzp.smartcampus.common.exception.SipException;
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
        LoginUserInfo loginUserInfo = USER_LOCAL.get();
        if (loginUserInfo == null) {
            throw new SipException("数据错误，用户未登录");
        }
        return loginUserInfo;
    }
}
