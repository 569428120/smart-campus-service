package com.xzp.smartcampus.portal.web;


import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.portal.service.IAuthService;
import com.xzp.smartcampus.portal.vo.LoginParamVo;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import com.xzp.smartcampus.system.model.RegionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统认证接口
 */
@RestController
@RequestMapping("/auth")
public class LoginUserController {

    @Resource
    private IAuthService authService;

    /**
     * 用户登录
     *
     * @param paramVo 用户名
     * @return ResponseEntity<String>
     */
    @PostMapping("/user/login")
    public ResponseEntity<Map<String, Object>> userLogin(@RequestBody LoginParamVo paramVo) {
        return ResponseEntity.ok(authService.userLogin(paramVo.getUserName(), paramVo.getPassword(), paramVo.getType()));
    }

    @PostMapping("/curr-user/gets")
    public ResponseEntity<LoginUserInfo> getLoginUser() {
        return ResponseEntity.ok(UserContext.getLoginUser());
    }

}
