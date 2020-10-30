package com.xzp.smartcampus.mobileapi.vo;

import com.xzp.smartcampus.system.model.MenuModel;
import lombok.Data;

/**
 * @author SGS
 */
@Data
public class AppInfo {

    /**
     * appId
     */
    private String id;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * app名称
     */
    private String appName;

    /**
     * 路由
     */
    private String url;

    /**
     * 图标路径
     */
    private String iconUrl;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建实例
     *
     * @param groupModel groupModel
     * @param item       item
     * @return AppInfo
     */
    public static AppInfo newInstance(MenuModel groupModel, MenuModel item) {
        AppInfo appInfo = new AppInfo();
        appInfo.setId(item.getId());
        appInfo.setAppName(item.getMenuName());
        appInfo.setUrl(item.getRoute());
        appInfo.setGroupId(item.getPid());
        appInfo.setGroupName(groupModel == null ? "缺失" : groupModel.getMenuName());
        appInfo.setIconUrl(item.getIconUrl());
        appInfo.setDescription(item.getDescription());
        return appInfo;
    }
}
