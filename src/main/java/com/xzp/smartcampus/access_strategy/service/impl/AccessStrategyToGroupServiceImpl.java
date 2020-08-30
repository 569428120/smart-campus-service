package com.xzp.smartcampus.access_strategy.service.impl;

import com.xzp.smartcampus.access_strategy.mapper.AccessStrategyToGroupMapper;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyToGroupModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyToGroupService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author SGS
 */
@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class AccessStrategyToGroupServiceImpl extends IsolationBaseService<AccessStrategyToGroupMapper, AccessStrategyToGroupModel> implements IAccessStrategyToGroupService {
}
