package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.mobileapi.model.LSExamineUserModel;

import java.util.List;

public interface ILsExamineUserService extends IBaseService {
    List<LSExamineUserModel> selectExamineUsers();
}
