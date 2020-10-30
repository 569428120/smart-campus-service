package com.xzp.smartcampus.authority.web;

import com.xzp.smartcampus.authority.param.CellLoginParam;
import com.xzp.smartcampus.authority.param.DeviceCodeLogin;
import com.xzp.smartcampus.authority.param.RoleSwitchParam;
import com.xzp.smartcampus.authority.param.WxOpenIdLoginParam;
import com.xzp.smartcampus.authority.service.ILoginService;
import com.xzp.smartcampus.authority.vo.LoginResultVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/authority/login")
public class LoginController {

    @Resource
    private ILoginService loginService;

    /**
     * 手机号码登录
     *
     * @param paramVo 登录参数
     * @return ResponseEntity
     */
    @PostMapping("/cell-number")
    public ResponseEntity<LoginResultVo> cellNumberLogin(@RequestBody @Valid CellLoginParam paramVo) {
        return ResponseEntity.ok(loginService.cellNumberLogin(paramVo));
    }

    /**
     * 微信公众号openId登录
     *
     * @param paramVo 登录参数
     * @return LoginResultVo
     */
    @PostMapping("/wx-openId")
    public ResponseEntity<LoginResultVo> wxOpenIdLogin(@RequestBody @Valid WxOpenIdLoginParam paramVo) {
        return ResponseEntity.ok(loginService.wxOpenIdLogin(paramVo));
    }

    /**
     * 硬件设备登录
     *
     * @param paramVo 登录参数
     * @return LoginResultVo
     */
    @PostMapping("/device_code")
    public ResponseEntity<LoginResultVo> deviceCodeLogin(@RequestBody @Valid DeviceCodeLogin paramVo) {
        return ResponseEntity.ok(loginService.deviceCodeLogin(paramVo));
    }

    /**
     * 角色切换
     *
     * @param paramVo 参数
     * @return LoginResultVo
     */
    @PostMapping("/role/switch")
    public ResponseEntity<LoginResultVo> roleSwitch(@RequestBody @Valid RoleSwitchParam paramVo) {
        return ResponseEntity.ok(loginService.roleSwitch(paramVo));
    }

}
