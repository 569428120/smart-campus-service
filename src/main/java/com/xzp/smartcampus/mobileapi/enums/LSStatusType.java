package com.xzp.smartcampus.mobileapi.enums;

/**
 * 一键放学审核状态
 */
public enum LSStatusType {
    AGREE("agree","同意"),
    APPROVAL("approval","待审核"),
    DISAGREE("disagree","不同意");

    private String key;
    private String desc;

    LSStatusType(String key, String desc) {
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
