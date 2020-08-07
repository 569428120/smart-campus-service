package com.xzp.smartcampus.common.enums;

/**
 * @author xuzhipeng
 */
public enum UserType {
    /**
     * 用户类型枚举
     */
    STUDENT("Student", "学生"),
    TEACHER("Teacher", "教师"),
    PARENT("Parent", "家长"),
    STAFF("Staff", "职员");

    private String key;
    private String desc;

    UserType(String key, String desc) {
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
