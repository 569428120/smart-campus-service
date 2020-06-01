package com.xzp.smartcampus.interceptor;

import com.xzp.smartcampus.common.utils.JwtUtil;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录拦截
 */
@Component
@Slf4j
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {


    /**
     * 登录拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
        /*   TODO 方便测试先注释, 硬件回调的接口都不做校验
        String token = request.getHeader("authentication");
        // 没有登录
        if (StringUtils.isBlank(token)) {
            response.sendError(401, "token is null");
            return false;
        }
        LoginUserInfo loginUserInfo = JwtUtil.getLoginUserByToken(token);
        // token失效
        if (loginUserInfo == null) {
            log.warn("token has expired");
            response.sendError(401, "token has expired");
            return false;
        }
        UserContext.setLoginUserInfo(loginUserInfo);
        return true;

         */
    }


}
