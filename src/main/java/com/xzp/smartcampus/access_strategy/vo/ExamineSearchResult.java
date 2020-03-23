package com.xzp.smartcampus.access_strategy.vo;

import lombok.Data;

/**
 * 门禁审核搜索 前台展示
 */
@Data
public class ExamineSearchResult {
    private String regionId;
    private String region;
    private String schoolId;
    private String school;

    private String flowId;      //流程主表id
    private String applicantId;
    private String applicantName;
    private String applicantType;
    private String applicantCode;
    private String currentStep;    //当前步骤id

    private String handleId;
    private String handleName;
    private String handleTime;
    private String reason;      //申请原因
    private String status;      //电子流状态

    private String createTime;
    private String startTime;   // 时间段开始
    private String endTime;     // 时间段结束时间

}
