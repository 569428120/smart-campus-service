package com.xzp.smartcampus.device.gate.enums;

public enum RunLogType {
    TEST_LOG("testLog", "测试日志"),
    RUN_LOG("runLog", "运行日志");
    private String key;
    private String desc;

    RunLogType(String key, String desc) {
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
