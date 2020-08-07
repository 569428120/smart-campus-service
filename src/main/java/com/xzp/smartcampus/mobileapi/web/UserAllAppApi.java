package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.mobileapi.service.IMobileAppService;
import com.xzp.smartcampus.mobileapi.vo.AppInfo;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi/authorized-all-app")
@Slf4j
public class UserAllAppApi {

    @Resource
    private IMobileAppService appService;

    /**
     * 获取有权限的所有app
     *
     * @return List<AppInfo>
     */
    @GetMapping("/gets")
    public ResponseEntity<List<AppInfo>> getAuthorityGroupPage() {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        if (userInfo == null) {
            log.warn("not login user");
            throw new SipException("当前用户未登录");
        }
        return ResponseEntity.ok(appService.getSelectedAppListByUserId(userInfo.getUserId()));
    }
}
