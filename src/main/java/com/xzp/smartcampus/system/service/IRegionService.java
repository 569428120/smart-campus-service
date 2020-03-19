package com.xzp.smartcampus.system.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.model.RegionModel;

/**
 * 教育局业务类
 */
public interface IRegionService extends IBaseService<RegionModel> {

    /**
     * 分页查询教育局数据
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return PageResult
     */
    PageResult getRegionListPage(RegionModel searchValue, Integer current, Integer pageSize);

    /**
     * 保存教育局数据
     *
     * @param regionModel regionModel
     * @return RegionModel
     */
    RegionModel postRegionModel(RegionModel regionModel);
}
