package com.xzp.smartcampus.access_strategy.service;

import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;

import java.util.List;

public interface IAccessStrategyService extends IBaseService<AccessStrategyModel> {

    PageResult findStrategyByCondition(String name, String status,Integer current,Integer pageSize);

    void deleteStrategyByIds(List<String> ids);
}
