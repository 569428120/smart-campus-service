package com.xzp.smartcampus.access_strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.access_strategy.mapper.CarAccessMapper;
import com.xzp.smartcampus.access_strategy.model.CarAccessRecordModel;
import com.xzp.smartcampus.access_strategy.service.ICarAccessRecordService;
import com.xzp.smartcampus.access_strategy.vo.CarAccessSearchParam;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class CarAccessRecordService extends IsolationBaseService<CarAccessMapper,CarAccessRecordModel>
        implements ICarAccessRecordService {

    public PageResult<CarAccessRecordModel> selectCarAccessRecord(
            CarAccessSearchParam param, Integer current, Integer pageSize) {
        PageResult<CarAccessRecordModel> carAccessRecordPageResult=this.selectPage(new Page<>(current,pageSize),new QueryWrapper<CarAccessRecordModel>()
                .like(StringUtils.isNotEmpty(param.getUserName()), "user_name", param.getUserName())
                .like(StringUtils.isNotEmpty(param.getUserCode()), "user_code", param.getUserCode())
                .like(StringUtils.isNotEmpty(param.getUserType()), "user_type", param.getUserType())
                .like(StringUtils.isNotEmpty(param.getStrategyType()), "strategy_type", param.getStrategyType())
                .like(StringUtils.isNotEmpty(param.getInOrOut()), "in_or_out", param.getInOrOut()));

        return carAccessRecordPageResult;
    }

}







