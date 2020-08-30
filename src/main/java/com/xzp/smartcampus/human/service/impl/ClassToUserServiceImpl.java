package com.xzp.smartcampus.human.service.impl;

import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.human.mapper.ClassToUserMapper;
import com.xzp.smartcampus.human.model.ClassToUserModel;
import com.xzp.smartcampus.human.service.IClassToUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ClassToUserServiceImpl extends IsolationBaseService<ClassToUserMapper, ClassToUserModel> implements IClassToUserService {
}
