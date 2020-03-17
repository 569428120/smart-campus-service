package com.xzp.smartcampus.system.enums;

public enum MenuType {
    PC_MENU("pc-menu", "PC端菜单"),
    MOBILE_MENU("mobile_menu", "手机端菜单");

    private String key;
    private String desc;

    MenuType(String key, String desc) {
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
