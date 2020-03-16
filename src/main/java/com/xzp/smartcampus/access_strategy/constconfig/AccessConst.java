package com.xzp.smartcampus.access_strategy.constconfig;

/**
 * 定义策略模块常量
 */
public class AccessConst {
    // 策略-时间段 更新动作标识
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String ADD = "add";

    // 流程审批状态
    public static final String PENDING = "pending";
    public static final String PROCESSING = "processing";
    public static final String FINISH = "finish";
    public static final String REFUSE = "refuse";

    // 审批步骤名称
    public static final String APPLY="apply";
    public static final String EXAMINE="examine";

}
