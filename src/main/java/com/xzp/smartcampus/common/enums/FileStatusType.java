package com.xzp.smartcampus.common.enums;

/**
 * @author xuzhipeng
 */
public enum FileStatusType {

    /**
     * 文件状态枚举
     */
    TO_BE_CONFIRMED("to_be_confirmed", "待确认"),
    ERROR("error", "错误"),
    DONE("done", "完成");

    private String key;
    private String desc;

    FileStatusType(String key, String desc) {
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
