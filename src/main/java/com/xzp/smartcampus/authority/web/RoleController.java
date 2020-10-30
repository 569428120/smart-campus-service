package com.xzp.smartcampus.authority.web;


import com.xzp.smartcampus.authority.service.IUserToRoleService;
import com.xzp.smartcampus.authority.vo.LoginUserToRoleVo;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xzp.smartcampus.mobileapi.vo.AppInfo;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/authority/role")
public class RoleController {

    @Resource
    private IUserToRoleService userToRoleService;

    @GetMapping("/gets/gets-by-cellNumber")
    public ResponseEntity<List<LoginUserToRoleVo>> getLoginUserToRoleList(@NotBlank(message = "手机号码不能为空") String cellNumber) {
        return ResponseEntity.ok(userToRoleService.getRolesByCellNumber(cellNumber));
    }

    @GetMapping("/menus/gets/gets-by-currUser")
    public ResponseEntity<List<AppInfo>> getAppInfosByUserId() {
        LoginUserInfo loginUserInfo = UserContext.getLoginUser();
        return ResponseEntity.ok(userToRoleService.getAppInfosByUserId(loginUserInfo));
    }
}
