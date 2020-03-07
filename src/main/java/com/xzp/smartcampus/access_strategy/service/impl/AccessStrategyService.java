package com.xzp.smartcampus.access_strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyMapper;
import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyTimeMapper;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyDetailModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class AccessStrategyService extends IsolationBaseService<AccessStrategyMapper, AccessStrategyModel>
        implements IAccessStrategyService{

    @Autowired
    AccessStrategyTimeMapper strategyTimeMapper;

    public List<AccessStrategyDetailModel> findStrategyByCondition(String name,String status) {
        List<AccessStrategyModel> strategyModels = new ArrayList<>();
        //条件为空时,查找所有记录;否则按条件查找
        QueryWrapper<AccessStrategyModel> wrapper = new QueryWrapper<>();
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(status)) {
            strategyModels = this.selectList(null);
        } else{
            if (!StringUtils.isEmpty(name)){
                wrapper.like("strategy_name",name);
            }
            if (!StringUtils.isEmpty(status)){
                wrapper.like("strategy_status",status);
            }
            strategyModels = this.selectList(wrapper);
        }
        // 组装策略详细信息
        List<AccessStrategyDetailModel> strategyDetailModels = new ArrayList<>();
        AccessStrategyDetailModel detailModel = new AccessStrategyDetailModel();
        if (!CollectionUtils.isEmpty(strategyModels)) {
            strategyModels.forEach(strategyModel -> {
                //拷贝原有策略信息
                BeanUtils.copyProperties(strategyModel, detailModel);

                //查询策略对应的日期类型和时间段信息(一个策略 对应 多个日期+起止时间)
                List<AccessStrategyTimeModel> strategyTimeModels = this.strategyTimeMapper.selectList(
                        new QueryWrapper<AccessStrategyTimeModel>().like("strategy_id", strategyModel.getId()));

                // 对策略下的日期限制进行分组 key:dateType;val:[startTime,endTime]
                List<HashMap<String,List<List<String>>>> periodModelMaps=new ArrayList<>();
                HashMap<String, List<List<String>>> periodMap=new HashMap<>();
                if (!CollectionUtils.isEmpty(strategyTimeModels)) {
                    strategyTimeModels.forEach(strategyTimeModel -> {
                        // 已有分组,直接添加起始时间值 数组格式
                        String dateType=strategyTimeModel.getDateType();
                        List<List<String>> periodList=new ArrayList<>();
                        if(periodMap.containsKey(dateType)){
                            periodList=periodMap.get(dateType);
                        }
                        // 没有分组时,直接添加 [起止时间]
                        periodList.add(Arrays.asList(strategyTimeModel.getStartTime(),strategyTimeModel.getEndTime()));
                        periodMap.put(dateType,periodList);
                    });
                    periodModelMaps.add(periodMap);
                }
                detailModel.setPeriodModelMaps(periodModelMaps);
            });
            strategyDetailModels.add(detailModel);
        }
        return strategyDetailModels;
    }


}
