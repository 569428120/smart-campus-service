package com.xzp.smartcampus.access_strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.access_strategy.constconfig.AccessConst;
import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyTimeMapper;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class AccessStrategyTimeService extends IsolationBaseService<AccessStrategyTimeMapper, AccessStrategyTimeModel> implements IAccessStrategyTimeService {

    public List<AccessStrategyTimeModel> findStrategyPeriod(String strategyId) {
        if (StringUtils.isEmpty(strategyId)) {
            log.error("Id of strategy is null or empty!");
            throw new SipException("Id of strategy is null or empty!");
        }
        return this.selectList(new QueryWrapper<AccessStrategyTimeModel>()
                .eq("strategy_id", strategyId)
                .orderByAsc("date_type")
        );
    }

    /**
     * 保存时间段
     *
     * @param strategyTimeModel strategyTimeModel
     * @return AccessStrategyTimeModel
     */
    @Override
    public AccessStrategyTimeModel saveAccessStrategyTime(AccessStrategyTimeModel strategyTimeModel) {
        if (StringUtils.isEmpty(strategyTimeModel.getId())) {
            return this.createStrategyTimeModel(strategyTimeModel);
        }
        return this.updateStrategyTimeModel(strategyTimeModel);
    }

    /**
     * 更新时间段
     *
     * @param strategyTimeModel strategyTimeModel
     * @return AccessStrategyTimeModel
     */
    private AccessStrategyTimeModel updateStrategyTimeModel(AccessStrategyTimeModel strategyTimeModel) {
        AccessStrategyTimeModel localModel = this.selectById(strategyTimeModel.getId());
        if (localModel == null) {
            log.error("not find AccessStrategyTimeModel by id {}", strategyTimeModel.getId());
            throw new SipException("数据错误，找不到AccessStrategyTimeModel根据id " + strategyTimeModel.getId());
        }
        localModel.setDateType(strategyTimeModel.getDateType());
        localModel.setStartTime(strategyTimeModel.getStartTime());
        localModel.setEndTime(strategyTimeModel.getEndTime());
        localModel.setDescription(strategyTimeModel.getDescription());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 新增时间段
     *
     * @param strategyTimeModel strategyTimeModel
     * @return AccessStrategyTimeModel
     */
    private AccessStrategyTimeModel createStrategyTimeModel(AccessStrategyTimeModel strategyTimeModel) {
        if (StringUtils.isEmpty(strategyTimeModel.getStrategyId())) {
            log.error("strategyId is null");
            throw new SipException("参数错误，strategyId不能为空");
        }
        strategyTimeModel.setId(SqlUtil.getUUId());
        this.insert(strategyTimeModel);
        return strategyTimeModel;
    }

}
