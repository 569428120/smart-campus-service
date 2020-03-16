package com.xzp.smartcampus.access_strategy.service.impl;

import com.xzp.smartcampus.access_strategy.mapper.AccessFlowPoolMapper;
import com.xzp.smartcampus.access_strategy.model.AccessFlowPoolModel;
import com.xzp.smartcampus.access_strategy.service.IAccessFlowPoolService;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccessFlowPoolService extends IsolationBaseService<AccessFlowPoolMapper, AccessFlowPoolModel>
        implements IAccessFlowPoolService {

}
