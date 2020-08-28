package com.xzp.smartcampus.common.api.ykt.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author SGS
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TktRecordVo {

    /**
     * 人员id
     */
    private String ryid;

    /**
     * 卡号
     */
    private String rfkh;

    /**
     * 消费方式
     */
    private String xffs;

    /**
     * 消费类型
     */
    private String xfzl;

    /**
     * 消费金额
     */
    private BigDecimal xfje;

    /**
     * 消费时间
     */
    private String xfsj;
}
