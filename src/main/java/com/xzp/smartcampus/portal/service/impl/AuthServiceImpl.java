package com.xzp.smartcampus.portal.service.impl;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.JsonUtils;
import com.xzp.smartcampus.common.utils.JwtUtil;
import com.xzp.smartcampus.portal.service.IAuthService;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AuthServiceImpl implements IAuthService {

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @param type     验证码
     * @return String
     */
    @Override
    public Map<String, String> userLogin(String userName, String password, String type) {
        Map<String, String> login = new HashMap<>();

        if ("admin".equals(userName) && "admin".equals(password)) {
            LoginUserInfo userInfo = new LoginUserInfo();
            userInfo.setUserId("test_userId");
            userInfo.setUserType("userType");
            Map<String, String> regionIdToName = new HashMap<>();
            regionIdToName.put("region_test", "耒阳市教育局");
            userInfo.setRegionIdToName(regionIdToName);
            Map<String, String> schoolIdToName = new HashMap<>();
            schoolIdToName.put("schoolIdToName", "XX小学");
            userInfo.setSchoolIdToName(schoolIdToName);

            login.put("status", "ok");
            login.put("type", type);
            login.put("currentAuthority", JwtUtil.sign(JsonUtils.toString(userInfo)));
            return login;
        }
        login.put("status", "error");
        login.put("type", type);
        login.put("currentAuthority", "");
        return login;
    }

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return LoginUserInfo
     */
    @Override
    public LoginUserInfo getLoginUserByToken(String token) {
        return null;
    }
}
