package com.xzp.smartcampus.mobileapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.mobileapi.mapper.LSExamineUserMapper;
import com.xzp.smartcampus.mobileapi.model.LSExamineUserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class LsExamineUserServiceImpl extends IsolationBaseService<LSExamineUserMapper,LSExamineUserModel> {

    public List<LSExamineUserModel> selectExamineUsers(){
        return this.selectList(new QueryWrapper<>());
    }

}

















