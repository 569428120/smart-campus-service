package com.xzp.smartcampus.access_strategy.service;

import com.xzp.smartcampus.access_strategy.model.AccessStrategyDetailModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;

import java.util.List;

public interface IAccessStrategyService extends IBaseService<AccessStrategyModel> {

    List<AccessStrategyDetailModel> findStrategyByCondition(String name, String status);

    void createAccessStrategy(AccessStrategyDetailModel strategyDetailModel);

    void deleteStrategyById(String id);
}
