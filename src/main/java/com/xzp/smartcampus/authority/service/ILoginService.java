package com.xzp.smartcampus.authority.service;

import com.xzp.smartcampus.authority.param.CellLoginParam;
import com.xzp.smartcampus.authority.param.DeviceCodeLogin;
import com.xzp.smartcampus.authority.param.RoleSwitchParam;
import com.xzp.smartcampus.authority.param.WxOpenIdLoginParam;
import com.xzp.smartcampus.authority.vo.LoginResultVo;

import javax.validation.Valid;

/**
 * @author SGS
 */
public interface ILoginService {

    /**
     * 手机号码登录
     *
     * @param paramVo 登录参数
     * @return LoginResultVo
     */
    LoginResultVo cellNumberLogin(@Valid CellLoginParam paramVo);

    /**
     * 微信公众号OpenId登录
     *
     * @param paramVo 登录参数
     * @return LoginResultVo
     */
    LoginResultVo wxOpenIdLogin(@Valid WxOpenIdLoginParam paramVo);

    /**
     * 硬件设备登录
     *
     * @param paramVo 登录参数
     * @return LoginResultVo
     */
    LoginResultVo deviceCodeLogin(@Valid DeviceCodeLogin paramVo);

    /**
     * 角色切换
     *
     * @param paramVo paramVo
     * @return LoginResultVo
     */
    LoginResultVo roleSwitch(@Valid RoleSwitchParam paramVo);
}
