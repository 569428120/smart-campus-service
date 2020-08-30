package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.param.CardPostParam;
import com.xzp.smartcampus.mobileapi.param.CardQueryParam;
import com.xzp.smartcampus.mobileapi.param.ExamineCardPostParam;
import com.xzp.smartcampus.mobileapi.vo.CardVo;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author SGS
 */
public interface ICardManageService {

    /**
     * 查询卡的列表
     *
     * @param param    搜索参数
     * @param current  当前页
     * @param pageSize 也数量
     * @return 数据
     */
    Map<String, Object> getCardPage(CardQueryParam param, Integer current, Integer pageSize);

    /**
     * 查询待审的列表
     *
     * @param param    搜索参数
     * @param current  当前页
     * @param pageSize 页数量
     * @return 数据
     */
    PageResult<CardVo> getExamineCardPage(CardQueryParam param, Integer current, Integer pageSize);

    /**
     * 保存卡数据
     *
     * @param param 参数
     */
    void saveCardData(CardPostParam param);

    /**
     * 删除卡的类型
     *
     * @param cardIds id
     */
    void deleteCardByIds(List<String> cardIds);

    /**
     * 审核卡
     *
     * @param param 参数
     */
    void examineCard(@Valid ExamineCardPostParam param);
}
