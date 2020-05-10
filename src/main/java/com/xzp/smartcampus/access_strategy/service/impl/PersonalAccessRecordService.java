package com.xzp.smartcampus.access_strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.access_strategy.mapper.PersonalAccessMapper;
import com.xzp.smartcampus.access_strategy.model.*;
import com.xzp.smartcampus.access_strategy.service.IPersonalAccessRecordService;
import com.xzp.smartcampus.access_strategy.vo.PersonalAccessSearchParam;
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
public class PersonalAccessRecordService extends IsolationBaseService<PersonalAccessMapper, PersonalAccessRecordModel>
        implements IPersonalAccessRecordService {

    public PageResult<PersonalAccessRecordModel> selectPersonalAccessRecord(
            PersonalAccessSearchParam param, Integer current, Integer pageSize) {

        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<PersonalAccessRecordModel>()
                .like(StringUtils.isNotEmpty(param.getUserName()), "user_name", param.getUserName())
                .like(StringUtils.isNotEmpty(param.getUserCode()), "user_code", param.getUserCode())
                .like(StringUtils.isNotEmpty(param.getUserType()), "user_type", param.getUserType())
                .like(StringUtils.isNotEmpty(param.getStrategyType()), "strategy_type", param.getStrategyType())
                .like(StringUtils.isNotEmpty(param.getMode()), "mode", param.getMode())
                .like(StringUtils.isNotEmpty(param.getInOrOut()), "in_or_out", param.getInOrOut()));
    }

}







