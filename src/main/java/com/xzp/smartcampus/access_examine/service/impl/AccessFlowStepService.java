package com.xzp.smartcampus.access_examine.service.impl;

import com.xzp.smartcampus.access_examine.mapper.AccessFlowStepMapper;
import com.xzp.smartcampus.access_examine.model.AccessFlowStepModel;
import com.xzp.smartcampus.access_examine.service.IAccessFlowStepService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = SipException.class)
public class AccessFlowStepService extends IsolationBaseService<AccessFlowStepMapper, AccessFlowStepModel>
        implements IAccessFlowStepService {
}
