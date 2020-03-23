package com.xzp.smartcampus.access_strategy.service.impl;

import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyControlMapper;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyControlModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyControlService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = SipException.class)
@Slf4j
public class AccessStrategyControlService
        extends IsolationBaseService<AccessStrategyControlMapper, AccessStrategyControlModel>
        implements IAccessStrategyControlService {


}
