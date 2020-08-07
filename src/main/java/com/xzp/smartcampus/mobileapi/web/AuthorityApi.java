package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.portal.service.IAuthService;
import com.xzp.smartcampus.portal.vo.LoginParamVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author xuzhipeng
 */
@RestController
@RequestMapping("/mobileApi/auth")
public class AuthorityApi {

    @Resource
    private IAuthService authService;

    @PostMapping("/user/login")
    public ResponseEntity<Map<String, Object>> userLogin(@RequestBody LoginParamVo paramVo) {
        return ResponseEntity.ok(authService.userLogin(paramVo.getUserName(), paramVo.getPassword(), paramVo.getType()));
    }

}
