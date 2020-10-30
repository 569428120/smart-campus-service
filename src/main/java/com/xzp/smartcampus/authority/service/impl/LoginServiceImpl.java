package com.xzp.smartcampus.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.authority.model.AtyUserModel;
import com.xzp.smartcampus.authority.param.CellLoginParam;
import com.xzp.smartcampus.authority.param.DeviceCodeLogin;
import com.xzp.smartcampus.authority.param.RoleSwitchParam;
import com.xzp.smartcampus.authority.param.WxOpenIdLoginParam;
import com.xzp.smartcampus.authority.service.ILoginService;
import com.xzp.smartcampus.authority.service.IUserToRoleService;
import com.xzp.smartcampus.authority.vo.LoginResultVo;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.JsonUtils;
import com.xzp.smartcampus.common.utils.JwtUtil;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.authority.service.IAtyUserService;
import com.xzp.smartcampus.human.service.ISelectUserService;
import com.xzp.smartcampus.human.vo.UserVo;
import com.xzp.smartcampus.portal.service.IAuthService;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Resource
    private IAtyUserService userService;

    @Resource
    private IUserToRoleService userToRoleService;

    @Resource
    private ISelectUserService selectUserService;

    @Resource
    private IAuthService authService;

    /**
     * 手机号码登录
     *
     * @param paramVo 登录参数
     * @return LoginResultVo
     */
    @Override
    public LoginResultVo cellNumberLogin(@Valid CellLoginParam paramVo) {
        // 登录的绑定数据
        List<AtyUserModel> userModels = userService.selectList(new QueryWrapper<AtyUserModel>()
                .eq("cell_number", paramVo.getCellNumber())
        );
        // 没有登录过
        if (CollectionUtils.isEmpty(userModels)) {
            return this.getLoginResultVo(this.createUserModelByCell(paramVo.getCellNumber()));
        }
        return this.getLoginResultVo(userModels.get(0));
    }

    /**
     * 转换为登录对象
     *
     * @param userModel 对象
     * @return LoginResultVo
     */
    private LoginResultVo getLoginResultVo(AtyUserModel userModel) {
        LoginResultVo loginResultVo = new LoginResultVo();
        loginResultVo.setBindUserId(userModel.getId());
        loginResultVo.setRoleVos(userToRoleService.getRolesByCellNumber(userModel.getCellNumber()));
        return loginResultVo;
    }

    /**
     * 创建用户根据手机号
     *
     * @param cellNumber 手机号码
     * @return UserModel
     */
    private AtyUserModel createUserModelByCell(@NotBlank(message = "手机号码不能为空") String cellNumber) {
        AtyUserModel userModel = new AtyUserModel();
        userModel.setId(SqlUtil.getUUId());
        userModel.setCellNumber(cellNumber);
        userService.insert(userModel);
        return userModel;
    }


    /**
     * 微信公众号OpenId登录
     *
     * @param paramVo 登录参数
     * @return LoginResultVo
     */
    @Override
    public LoginResultVo wxOpenIdLogin(@Valid WxOpenIdLoginParam paramVo) {
        return null;
    }

    /**
     * 硬件设备登录
     *
     * @param paramVo 登录参数
     * @return LoginResultVo
     */
    @Override
    public LoginResultVo deviceCodeLogin(@Valid DeviceCodeLogin paramVo) {
        return null;
    }

    /**
     * 角色切换
     *
     * @param paramVo paramVo
     * @return LoginResultVo
     */
    @Override
    public LoginResultVo roleSwitch(@Valid RoleSwitchParam paramVo) {
        // 获取用户
        UserVo userVo = selectUserService.getUserVoById(paramVo.getPartId());
        if (userVo == null) {
            log.warn("not find user by id {}", paramVo.getPartId());
            throw new SipException("参数错误，找不到用户 " + paramVo.getPartId());
        }
        LoginResultVo resultVo = new LoginResultVo();
        LoginUserInfo userInfo = authService.createLoginUser(userVo);
        BeanUtils.copyProperties(userInfo, resultVo);
        resultVo.setAuthentication(JwtUtil.sign(JsonUtils.toString(userInfo)));
        return resultVo;
    }
}
