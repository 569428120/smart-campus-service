package com.xzp.smartcampus.mobileapi.vo;

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
}
