package com.xzp.smartcampus.access_strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyMapper;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
     *
     * @param name   策略名称关键字
     * @param status 策略状态关键字
     * @return
     */
    public PageResult<AccessStrategyModel> findStrategyByCondition(
            String name, String status, Integer current, Integer pageSize) {
        // 1.条件为空时,查找所有策略记录;否则按条件查找
        PageResult<AccessStrategyModel> strategyModePageResult = this.selectPage(new Page<>(current,pageSize),
                    new QueryWrapper<AccessStrategyModel>()
                        .like(StringUtils.isNotEmpty(name), "strategy_name", name)
                        .like(StringUtils.isNotEmpty(status), "strategy_status", status));
        if (CollectionUtils.isEmpty(strategyModePageResult.getData())){
            return new PageResult<>(0L,Collections.emptyList());
        }else{
            return strategyModePageResult;
        }

    }


    /**
     * 根据策略id列表删除对应的策略及策略下的时间段信息
     * @param ids
     */
    public void deleteStrategyByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("Ids of strategy to be deleted are null or empty!");
            throw new SipException("Ids of strategy to be deleted are null or empty!");
        }
        // 1.删除策略主表数据
        this.deleteByIds(ids);
        log.info(MessageFormat.format("Strategy :{0} have been deleted", ids));
        // 2.删除对应的 策略-时间段 数据
        ids.forEach(id->{
            this.strategyTimeService.delete(new UpdateWrapper<AccessStrategyTimeModel>().like("strategy_id", id));
            log.info(MessageFormat.format("Strategy-time-models that belongs strategy-id:{0} have been deleted", id));
        });
    }

    public void modifyAccessStrategy(AccessStrategyModel strategyModel) {
        this.updateById(strategyModel);
    }


}







