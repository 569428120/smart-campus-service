package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.mobileapi.vo.AppInfo;

import java.util.List;

/**
 * @author SGS
 */
public interface IMobileAppService {

    /**
     * 获取用户选择的app列表
     *
     * @param userId userId
     * @return List<AppInfo>
     */
    List<AppInfo> getSelectedAppListByUserId(String userId);
}
