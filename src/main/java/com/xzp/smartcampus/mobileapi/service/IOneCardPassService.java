package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.param.MyCardParam;
import com.xzp.smartcampus.mobileapi.param.CrQueryParam;
import com.xzp.smartcampus.mobileapi.param.QuotaParam;
import com.xzp.smartcampus.mobileapi.vo.AmountVo;
import com.xzp.smartcampus.mobileapi.vo.CardVo;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Administrator
 */
public interface IOneCardPassService {

    /**
     * 或者我的卡列表，如果是家长就把学生的卡都展示出来
     *
     * @return List<CardVo>
     */
    List<CardVo> getMyCardList();

    /**
     * 查询消费记录
     *
     * @param queryParam 查询条件
     * @param current    当前页
     * @param pageSize   每页大小
     * @return PageResult<AmountVo>
     */
    PageResult<AmountVo> getConsumptionRecord(CrQueryParam queryParam, Integer current, Integer pageSize);

    /**
     * 查询充值记录
     *
     * @param queryParam 查询条件
     * @param current    当前页
     * @param pageSize   每页大小
     * @return PageResult<AmountVo>
     */
    PageResult<AmountVo> getRechargeRecord(CrQueryParam queryParam, Integer current, Integer pageSize);

    /**
     * 创建我的卡申请
     *
     * @param cardParam 参数
     */
    void saveMyCard(MyCardParam cardParam);

    /**
     * 限额
     *
     * @param quotaParam 参数
     */
    void saveCardQuota(@Valid QuotaParam quotaParam);
}
