package com.xzp.smartcampus.test.service.impl;

import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.test.mapper.TestMapper;
import com.xzp.smartcampus.test.model.TestModel;
import com.xzp.smartcampus.test.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * 这边假设  需要数据隔离（是否数据隔离请查看文档）
 */
@Service
public class TestService extends IsolationBaseService<TestMapper, TestModel> implements ITestService {
}
