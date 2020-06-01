package com.xzp.smartcampus.system.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.model.SchoolModel;
import com.xzp.smartcampus.system.vo.SchoolVo;

/**
 * 学校业务
 */
public interface ISchoolService extends IBaseService<SchoolModel> {

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    每页显示的数量
     * @return PageResult
     */
    PageResult getSchoolListPage(SchoolModel searchValue, Integer current, Integer pageSize);

    /**
     * 保存或者更新
     *
     * @param schoolVo schoolVo
     * @return SchoolModel
     */
    SchoolVo postSchoolModel(SchoolVo schoolVo);
}
