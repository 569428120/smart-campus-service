package com.xzp.smartcampus.device.gate.enums;


public enum TestStatus {
    NORMAL("normal", "运行中"),
    EXCEPTION("exception", "失败"),
    SUCCESS("success", "成功");
    private String key;
    private String desc;

    TestStatus(String key, String desc) {
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
