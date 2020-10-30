package com.xzp.smartcampus.authority.service.impl;

import com.xzp.smartcampus.authority.mapper.AtyUserMapper;
import com.xzp.smartcampus.authority.model.AtyUserModel;
import com.xzp.smartcampus.authority.service.IAtyUserService;
import com.xzp.smartcampus.common.service.NonIsolationBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AtyUserServiceImpl extends NonIsolationBaseService<AtyUserMapper, AtyUserModel> implements IAtyUserService {

}
