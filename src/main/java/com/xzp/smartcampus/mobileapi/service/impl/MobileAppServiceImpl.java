package com.xzp.smartcampus.mobileapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.mobileapi.service.IMobileAppService;
import com.xzp.smartcampus.mobileapi.vo.AppInfo;
import com.xzp.smartcampus.system.model.MenuModel;
import com.xzp.smartcampus.system.service.IMobileMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MobileAppServiceImpl implements IMobileAppService {

    @Resource
    private IMobileMenuService mobileMenuService;

    /**
     * 获取用户选择的app列表 二级菜单为app TODO 目前app不多全部展示
     *
     * @param userId userId
     * @return List<AppInfo>
     */
    @Override
    public List<AppInfo> getSelectedAppListByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            log.warn("userId is null");
            return Collections.emptyList();
        }
        // TODO 先不做权限控制全部查出来
        List<MenuModel> groupModels = mobileMenuService.selectList(new QueryWrapper<MenuModel>()
                .eq("menu_level", 1)
        );
        Map<String, MenuModel> groupIdToModelMap = CollectionUtils.isEmpty(groupModels) ?
                Collections.emptyMap() :
                groupModels.stream().collect(Collectors.toMap(MenuModel::getId, v -> v));

        List<MenuModel> menuModels = mobileMenuService.selectList(new QueryWrapper<MenuModel>()
                .eq("menu_level", 2)
        );
        if (CollectionUtils.isEmpty(menuModels)) {
            log.info("app list is null");
            return Collections.emptyList();
        }
        return menuModels.stream().map(item -> {
            MenuModel groupModel = groupIdToModelMap.get(item.getPid());
            AppInfo appInfo = new AppInfo();
            appInfo.setId(item.getId());
            appInfo.setAppName(item.getMenuName());
            appInfo.setUrl(item.getRoute());
            appInfo.setGroupId(item.getPid());
            appInfo.setGroupName(groupModel == null ? "缺失" : groupModel.getMenuName());
            appInfo.setIconUrl(item.getIconUrl());
            appInfo.setDescription(item.getDescription());
            return appInfo;
        }).collect(Collectors.toList());
    }
}
