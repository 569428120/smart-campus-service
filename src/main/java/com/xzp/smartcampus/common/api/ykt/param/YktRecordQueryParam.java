package com.xzp.smartcampus.common.api.ykt.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author SGS
 */
@Data
public class YktRecordQueryParam {

    public static final String XFZL_XF = "消费";

    public static final String XFZL_ZK = "增款";


    /**
     * IC 卡号
     */
    @NotNull(message = "卡号 rfkh 不能为空")
    private String rfkh;

    /**
     * 员工编号
     */
    @NotNull(message = "人员编号 rybh 不能为空")
    private String rybh;

    /**
     * 消费种类 消费，增款，减款
     */
    private String xfzl;

    /**
     * 开始时间
     */
    private String begintime;


    /**
     * 结束时间
     */
    private String endtime;


    /**
     * 页
     */
    private String pagesize;

    /**
     * 当前页
     */
    private String pageindex;

}
