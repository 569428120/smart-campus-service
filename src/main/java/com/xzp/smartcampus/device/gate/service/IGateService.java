package com.xzp.smartcampus.device.gate.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.system.vo.GateVo;

/**
 * 门禁设备业务
 *
 * @author xuzhipeng
 */
public interface IGateService extends IBaseService<GateModel> {

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    每页显示的数量
     * @return PageResult<GateModel>
     */
    PageResult<GateVo> getGateListPage(GateModel searchValue, Integer current, Integer pageSize);

    /**
     * 保存
     *
     * @param gateModel gateModel
     * @return GateModel
     */
    GateModel saveGateModel(GateModel gateModel);

    /**
     * 校验数据
     *
     * @param gateModel gateModel
     */
    void validatorGate(GateModel gateModel);
}
