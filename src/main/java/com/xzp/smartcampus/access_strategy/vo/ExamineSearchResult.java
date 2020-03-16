package com.xzp.smartcampus.access_strategy.vo;

import lombok.Data;

/**
 * 门禁审核搜索 前台展示
 */
@Data
public class ExamineSearchResult {
    private String applicantName;
    private String region;
    private String school;
    private String applicantType;
    private String applicantCode;

    private String createTime;
    private String handleTime;
    private String handleName;
    private String reason;
    private String status;
    private String period;

}
