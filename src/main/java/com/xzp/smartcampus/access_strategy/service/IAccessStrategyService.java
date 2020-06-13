package com.xzp.smartcampus.access_strategy.service;

import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;

import java.util.List;

public interface IAccessStrategyService extends IBaseService<AccessStrategyModel> {

    /**
     * 分页查询
     *
     * @param name     名称
     * @param status   状态
     * @param current  当前页
     * @param pageSize 页容量
     * @return PageResult
     */
    PageResult findStrategyByCondition(String name, String status, Integer current, Integer pageSize);

    /**
     * 删除策略
     *
     * @param ids 主键id
     */
    void deleteStrategyByIds(List<String> ids);

    /**
     * 保存策略
     *
     * @param strategyModel strategyModel
     */
    AccessStrategyModel saveAccessStrategy(AccessStrategyModel strategyModel);

    /**
     * 更新策略的状态
     *
     * @param strategyId strategyId
     * @param status     status
     */
    void updateAccessStrategyStatus(String strategyId, String status);
}
