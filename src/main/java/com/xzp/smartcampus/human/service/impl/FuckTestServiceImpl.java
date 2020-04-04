package com.xzp.smartcampus.human.service.impl;

import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.human.mapper.FuckTestMapper;
import com.xzp.smartcampus.human.model.FuckTestModel;
import com.xzp.smartcampus.human.service.FuckTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FuckTestServiceImpl extends IsolationBaseService<FuckTestMapper, FuckTestModel> implements FuckTestService {

}
