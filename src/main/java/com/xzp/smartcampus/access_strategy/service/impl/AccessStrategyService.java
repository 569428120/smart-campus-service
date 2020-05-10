package com.xzp.smartcampus.access_strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyMapper;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyDetailModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;

@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class AccessStrategyService extends IsolationBaseService<AccessStrategyMapper, AccessStrategyModel>
        implements IAccessStrategyService {

    @Autowired
    IAccessStrategyTimeService strategyTimeService;

    /**
     * 根据搜索条件(名称和状态)查找对应的策略
     * @param name    策略名称关键字
     * @param status  策略状态关键字
     * @return
     */
    public List<AccessStrategyDetailModel> findStrategyByCondition(String name, String status) {
        List<AccessStrategyModel> strategyModels = new ArrayList<>();
        //条件为空时,查找所有记录;否则按条件查找
        PageResult<AccessStrategyModel> strategyModePageResult;
        QueryWrapper<AccessStrategyModel> wrapper = new QueryWrapper<>();

        wrapper.like(StringUtils.isNotEmpty(name),"strategy_name", name)
                .like(StringUtils.isNotEmpty(status),"strategy_status", status);
        strategyModels = this.selectList(wrapper);

        // 组装策略详细信息
        List<AccessStrategyDetailModel> strategyDetailModels = new ArrayList<>();
        AccessStrategyDetailModel detailModel = new AccessStrategyDetailModel();
        if (!CollectionUtils.isEmpty(strategyModels)) {
            strategyModels.forEach(strategyModel -> {
                //拷贝原有策略信息
                BeanUtils.copyProperties(strategyModel, detailModel);

                //查询策略对应的日期类型和时间段信息(一个策略 对应 多个日期+起止时间)
                List<AccessStrategyTimeModel> strategyTimeModels = this.strategyTimeService.selectList(
                        new QueryWrapper<AccessStrategyTimeModel>().like("strategy_id", strategyModel.getId()));

                // 对策略下的日期限制进行分组 key:dateType;val:[startTime,endTime]
                List<Map<String, List<List<String>>>> periodModelMaps = new ArrayList<>();
                HashMap<String, List<List<String>>> periodMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(strategyTimeModels)) {
                    strategyTimeModels.forEach(strategyTimeModel -> {
                        // 已有分组,直接添加起始时间值 数组格式
                        log.info("This date-type has been in the group of strategy-time,just add it to the time-period list");
                        String dateType = strategyTimeModel.getDateType();
                        List<List<String>> periodList = new ArrayList<>();
                        if (periodMap.containsKey(dateType)) {
                            periodList = periodMap.get(dateType);
                        }
                        // 没有分组时,直接添加 [起止时间]
                        log.info("This date-type is not in the group of strategy-time,try to create a new group");
                        periodList.add(Arrays.asList(strategyTimeModel.getStartTime(), strategyTimeModel.getEndTime()));
                        periodMap.put(dateType, periodList);
                    });
                    periodModelMaps.add(periodMap);
                }
                detailModel.setPeriodModelMaps(periodModelMaps);
            });
            strategyDetailModels.add(detailModel);
        }
        return strategyDetailModels;
    }

    /**
     * 创建新的策略
     * @param strategyDetailModel  策略详细信息
     */
    @Override
    public void createAccessStrategy(AccessStrategyDetailModel strategyDetailModel){
        //1. 策略实体属性直接使用策略详情的属性拷贝
        AccessStrategyModel strategyModel=new AccessStrategyModel();
        BeanUtils.copyProperties(strategyDetailModel,strategyModel);
        //2.策略时间model则通过策略详情的信息进行解析
        List<Map<String, List<List<String>>>> periodMaps=strategyDetailModel.getPeriodModelMaps();
        List<AccessStrategyTimeModel> strategyTimeModels=new ArrayList<>();
        periodMaps.forEach(periodMap->{
            for(Map.Entry<String,List<List<String>>> entry:periodMap.entrySet()){
                // 遍历值列表,每个元素[start_time,end_time]即为一对起止时间
                entry.getValue().forEach(time_list->{
                    AccessStrategyTimeModel strategyTimeModel=new AccessStrategyTimeModel();
                    // 日期类型date_type
                    strategyTimeModel.setDateType(entry.getKey());
                    if (time_list.size()==2){
                        strategyTimeModel.setStartTime(time_list.get(0));
                        strategyTimeModel.setEndTime(time_list.get(1));
                    }else{
                        log.error(MessageFormat.format("Start-time or end-time is null of the list:{0},date type is{1}",time_list,entry.getKey()));
                        throw new SipException("Start time or end time of the strategy is missing");
                    }

                    strategyTimeModels.add(strategyTimeModel);
                });
            }
        });
        //3.将策略Model写入数据库
//        if (!CollectionUtils.isEmpty(strategyTimeModels)){  //如果策略时间Model为空则不记录该条策略>>>暂不做此限制
        this.insert(strategyModel);
        //4.待策略Model写入之后取得策略Model的Id
        strategyTimeModels.forEach(time_model->{
            time_model.setRegionId(strategyModel.getRegionId());
            time_model.setSchoolId(strategyModel.getSchoolId());
            time_model.setStrategyId(strategyModel.getId());
        });
        //5.策略-时间 Model写入数据库
        log.info("Insert strategyTimeModels into database,the corresponding strategy-id is: "+strategyModel.getId());
        this.strategyTimeService.insertBatch(strategyTimeModels);
//        }
    }

    public void deleteStrategyById(String id) {
        if(StringUtils.isEmpty(id)){
            log.error("Id of strategy to be deleted is null or empty!");
            throw new SipException("Id of strategy to be deleted is null or empty!");
        }
        // 1.删除策略主表数据
        this.deleteById(id);
        log.info(MessageFormat.format("Strategy id:{0} has been deleted",id));
        // 2.删除对应的 策略-时间段 数据
        this.strategyTimeService.delete(new UpdateWrapper<AccessStrategyTimeModel>().like("strategy_id",id));
        log.info(MessageFormat.format("Strategy-time-models that belongs strategy-id:{0} have been deleted",id));
    }

    public void modifyAccessStrategy(AccessStrategyModel strategyModel){
        this.updateById(strategyModel);
    }



























}







