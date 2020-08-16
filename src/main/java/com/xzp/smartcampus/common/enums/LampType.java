package com.xzp.smartcampus.common.enums;

/**
 * @author SGS
 */
public enum LampType {

    /**
     *
     */
    HOME("home", "主页"),
    /**
     *
     */
    RESOURCE_HOME("resource-home", "资源页"),
    /**
     *
     */
    APP_HOME("app-home", "app页");

    private String key;
    private String desc;

    LampType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
