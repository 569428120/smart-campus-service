package com.xzp.smartcampus.access_strategy.service;

import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.common.service.IBaseService;

import java.util.List;

public interface IAccessStrategyTimeService extends IBaseService<AccessStrategyTimeModel> {

    void modifyAccessStrategyTime(List<AccessStrategyTimeModel> strategyTimeModels);

    List<AccessStrategyTimeModel> findStrategyPeriod(String strategyId);
}
