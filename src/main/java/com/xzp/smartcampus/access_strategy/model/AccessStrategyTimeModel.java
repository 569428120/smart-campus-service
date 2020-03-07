package com.xzp.smartcampus.access_strategy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_access_ser_strategy_time")
public class AccessStrategyTimeModel extends BaseModel {
    //日期类型:节假日,工作日,无限期
    private String dateType;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;

}
