package com.xzp.smartcampus.device.gate.enums;

public enum ManufacturerType {
    GATE("gate", "门禁厂商");
    private String key;
    private String desc;

    ManufacturerType(String key, String desc) {
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
