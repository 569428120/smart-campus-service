package com.xzp.smartcampus.mobileapi.vo;

import lombok.Data;

@Data
public class LSRecordSearchParam {
    //班级Id
    private String classId;
    //开始时间
    private String startTime;
    private String endTime;
}
