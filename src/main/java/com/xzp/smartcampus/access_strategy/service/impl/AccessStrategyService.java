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
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;


@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class AccessStrategyService extends IsolationBaseService<AccessStrategyMapper, AccessStrategyModel> implements IAccessStrategyService {

    @Resource
    private IAccessStrategyTimeService strategyTimeService;

    /**
     * 根据搜索条件(名称和状态)查找对应的策略
     *
     * @param name   策略名称关键字
     * @param status 策略状态关键字
     * @return PageResult<AccessStrategyModel>
     */
    public PageResult<AccessStrategyModel> findStrategyByCondition(String name, String status, Integer current, Integer pageSize) {
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<AccessStrategyModel>()
                .eq(StringUtils.isNotEmpty(status), "strategy_status", status)
                .like(StringUtils.isNotEmpty(name), "strategy_name", name)
                .orderByDesc("create_time")
        );
    }


    /**
     * 根据策略id列表删除对应的策略及策略下的时间段信息
     *
     * @param ids ids
     */
    public void deleteStrategyByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.warn("ids is null");
            return;
        }
        List<AccessStrategyModel> accessStrategyModels = this.selectByIds(ids);
        if (CollectionUtils.isEmpty(accessStrategyModels)) {
            log.info("not find accessStrategyModels by ids {}", ids);
            return;
        }
        strategyTimeService.delete(new UpdateWrapper<AccessStrategyTimeModel>()
                .in("strategy_id", ids)
        );
        // 删除策略主表数据
        this.deleteByIds(ids);
    }

    /**
     * 保存策略
     *
     * @param strategyModel strategyModel
     */
    @Override
    public AccessStrategyModel saveAccessStrategy(AccessStrategyModel strategyModel) {
        if (StringUtils.isBlank(strategyModel.getId())) {
            return this.createAccessStrategy(strategyModel);
        }
        return this.updateAccessStrategy(strategyModel);
    }

    /**
     * 更新策略的状态
     *
     * @param strategyId strategyId
     * @param status     status
     */
    @Override
    public void updateAccessStrategyStatus(String strategyId, String status) {
        if (StringUtils.isBlank(strategyId) || StringUtils.isBlank(status)) {
            log.warn("strategyId or status is null");
            return;
        }
        AccessStrategyModel model = this.selectById(strategyId);
        if (model == null) {
            log.error("not find AccessStrategyModel strategyId {}", strategyId);
            throw new SipException("参数错误，找不到AccessStrategyModel根据id " + strategyId);
        }
        model.setStrategyStatus(status);
        this.updateById(model);
    }

    /**
     * 更新策略
     *
     * @param strategyModel strategyModel
     * @return AccessStrategyModel
     */
    private AccessStrategyModel updateAccessStrategy(AccessStrategyModel strategyModel) {
        AccessStrategyModel localModel = this.selectById(strategyModel.getId());
        if (localModel == null) {
            log.error("localModel is null by id {}", strategyModel.getId());
            throw new SipException("数据错误，找不到AccessStrategyModel id " + strategyModel.getId());
        }
        localModel.setStrategyName(strategyModel.getStrategyName());
        localModel.setStrategyStatus(strategyModel.getStrategyStatus());
        localModel.setDescription(strategyModel.getDescription());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 创建策略
     *
     * @param strategyModel strategyModel
     * @return AccessStrategyModel
     */
    private AccessStrategyModel createAccessStrategy(AccessStrategyModel strategyModel) {
        strategyModel.setId(SqlUtil.getUUId());
        strategyModel.setStrategyCode(strategyModel.getId());
        strategyModel.setStrategyStatus("UnEnable"); // TODO 换成枚举 Enable  UnEnable
        this.insert(strategyModel);
        return strategyModel;
    }

}







