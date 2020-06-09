package com.xzp.smartcampus.access_strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.access_strategy.constconfig.AccessConst;
import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyTimeMapper;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
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
public class AccessStrategyTimeService extends IsolationBaseService<AccessStrategyTimeMapper, AccessStrategyTimeModel>
        implements IAccessStrategyTimeService {

    public void modifyAccessStrategyTime(List<AccessStrategyTimeModel> strategyTimeModels) {
        // 1.根据策略-时间段 model提供的action,将model分为三组,新增,更新,删除
        List<AccessStrategyTimeModel> addTimeModels = new ArrayList<>();
        List<AccessStrategyTimeModel> upTimeModels = new ArrayList<>();
        List<String> delTimeModelIds = new ArrayList<>();
        strategyTimeModels.forEach(timeModel -> {

            if (timeModel.getAction().equals(AccessConst.ADD)) {
                addTimeModels.add(timeModel);
            } else if (timeModel.getAction().equals(AccessConst.UPDATE)) {
                upTimeModels.add(timeModel);
            } else if (timeModel.getAction().equals(AccessConst.DELETE)) {
                delTimeModelIds.add(timeModel.getId());
            }
        });
        // 2.根据分组进行相应的增删改操作
        if (!CollectionUtils.isEmpty(addTimeModels)) {
            this.insertBatch(addTimeModels);
        }
        if (!CollectionUtils.isEmpty(upTimeModels)) {
            this.updateBatch(upTimeModels);
        }
        if (!CollectionUtils.isEmpty(delTimeModelIds)) {
            this.deleteByIds(delTimeModelIds);
        }

    }

    public List<AccessStrategyTimeModel> findStrategyPeriod(String strategyId){
        if (StringUtils.isEmpty(strategyId)){
            log.error("Id of strategy is null or empty!");
            throw new SipException("Id of strategy is null or empty!");
        }

        return this.selectList(new QueryWrapper<AccessStrategyTimeModel>().like("strategy_id",strategyId));
    }

}
