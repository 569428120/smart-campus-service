package com.xzp.smartcampus.access_strategy.service;


import com.xzp.smartcampus.access_strategy.model.CarAccessRecordModel;
import com.xzp.smartcampus.access_strategy.vo.CarAccessSearchParam;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;

public interface ICarAccessRecordService extends IBaseService<CarAccessRecordModel> {
    PageResult<CarAccessRecordModel> selectCarAccessRecord(
            CarAccessSearchParam param, Integer current, Integer pageSize);

}
