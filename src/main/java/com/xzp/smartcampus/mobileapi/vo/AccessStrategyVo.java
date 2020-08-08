package com.xzp.smartcampus.mobileapi.vo;

import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author SGS
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessStrategyVo extends AccessStrategyModel {

    /**
     * 分组列表
     */
    private List<UserGroupVo> groupList;

    private List<AccessStrategyTimeModel> timeQuantumList;
}
