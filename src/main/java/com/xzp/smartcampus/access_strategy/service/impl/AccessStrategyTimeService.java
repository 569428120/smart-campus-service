package com.xzp.smartcampus.access_strategy.service.impl;

import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyTimeMapper;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import org.springframework.stereotype.Service;

@Service
public class AccessStrategyTimeService extends IsolationBaseService<AccessStrategyTimeMapper, AccessStrategyTimeModel>
        implements IAccessStrategyTimeService {

}
