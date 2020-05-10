package com.xzp.smartcampus.access_examine.vo;

import lombok.Data;

/**
 * 审批数据参数
 */
@Data
public class ExamineFlowParam {
    private String serviceId;
    private String flowId;
    private String stepId;
    private String status;
    private String opinion;
}
