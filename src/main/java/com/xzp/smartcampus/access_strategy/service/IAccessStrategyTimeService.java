package com.xzp.smartcampus.access_strategy.service;

import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.common.service.IBaseService;

import java.util.List;

public interface IAccessStrategyTimeService extends IBaseService<AccessStrategyTimeModel> {

    List<AccessStrategyTimeModel> findStrategyPeriod(String strategyId);

    /**
     * 保存时间段
     *
     * @param strategyTimeModel strategyTimeModel
     * @return AccessStrategyTimeModel
     */
    AccessStrategyTimeModel saveAccessStrategyTime(AccessStrategyTimeModel strategyTimeModel);
}
