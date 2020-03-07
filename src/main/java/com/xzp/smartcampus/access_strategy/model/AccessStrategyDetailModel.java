package com.xzp.smartcampus.access_strategy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;

/**
 * 策略详情类,用以扩展AccessStrategyModel,添加日期类型和对应时间段
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessStrategyDetailModel extends AccessStrategyModel{
    private List<HashMap<String,List<List<String>>>> periodModelMaps;
}
