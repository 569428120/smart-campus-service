package com.xzp.smartcampus.authority.service.impl;

import com.xzp.smartcampus.authority.mapper.RoleMapper;
import com.xzp.smartcampus.authority.model.RoleModel;
import com.xzp.smartcampus.authority.service.IRoleService;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RoleServiceImpl extends IsolationBaseService<RoleMapper, RoleModel> implements IRoleService {

}
