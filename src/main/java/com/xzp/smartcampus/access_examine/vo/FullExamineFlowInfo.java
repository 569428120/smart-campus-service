package com.xzp.smartcampus.access_examine.vo;

import com.xzp.smartcampus.access_examine.model.AccessFlowPoolModel;
import com.xzp.smartcampus.access_examine.model.AccessFlowStepModel;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 单个流程业务对应的全量信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FullExamineFlowInfo  extends BaseModel {
    // 业务本身信息
    private String flowId;
    private String startTime;
    private String endTime;
    private Integer isCar;
    private String carNumber;
    //业务下的流程对象
    private AccessFlowPoolModel poolModel;
    //流程下对应的步骤对象
    private List<AccessFlowStepModel> steps;

}
