package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.vo.AccessStrategyVo;

import java.util.List;

/**
 * @author SGS
 */
public interface IMobileAccessStrategyService {

    /**
     * 查询策略列表
     *
     * @param searchParam 搜索条件
     * @param current     当前页
     * @param pageSize    页数量
     * @return PageResult
     */
    PageResult<AccessStrategyVo> getAccessStrategyPage(AccessStrategyVo searchParam, Integer current, Integer pageSize);

    /**
     * 保存策略
     *
     * @param strategyVo 数据
     */
    void saveAccessStrategy(AccessStrategyVo strategyVo);

    /**
     * 策略分配
     *
     * @param strategyId 策略id
     * @param groupIds   分组id
     */
    void saveStrategyToGroupIds(String strategyId, List<String> groupIds);

    /**
     * 删除策略
     *
     * @param strategyIds 策略id
     */
    void deleteStrategyByIds(List<String> strategyIds);
}
