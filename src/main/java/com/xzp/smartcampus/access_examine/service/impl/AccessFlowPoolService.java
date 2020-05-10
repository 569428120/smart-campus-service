package com.xzp.smartcampus.access_examine.service.impl;

import com.xzp.smartcampus.access_examine.mapper.AccessFlowPoolMapper;
import com.xzp.smartcampus.access_examine.model.AccessFlowPoolModel;
import com.xzp.smartcampus.access_examine.service.IAccessFlowPoolService;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccessFlowPoolService extends IsolationBaseService<AccessFlowPoolMapper, AccessFlowPoolModel>
        implements IAccessFlowPoolService {

}
