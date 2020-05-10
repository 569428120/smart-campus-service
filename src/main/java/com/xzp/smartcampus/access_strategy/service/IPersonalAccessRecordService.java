package com.xzp.smartcampus.access_strategy.service;


import com.xzp.smartcampus.access_strategy.model.PersonalAccessRecordModel;
import com.xzp.smartcampus.access_strategy.vo.PersonalAccessSearchParam;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;


public interface IPersonalAccessRecordService extends IBaseService<PersonalAccessRecordModel> {
    PageResult<PersonalAccessRecordModel> selectPersonalAccessRecord(
            PersonalAccessSearchParam param, Integer current, Integer pageSize);
}
