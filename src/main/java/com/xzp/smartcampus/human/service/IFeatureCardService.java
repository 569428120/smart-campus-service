package com.xzp.smartcampus.human.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.CardModel;
import com.xzp.smartcampus.human.vo.FeatureCardVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;

import java.util.List;

public interface IFeatureCardService extends IBaseService<CardModel> {

    /**
     * 分页查询
     *
     * @param searchValue searchValue
     * @param current     当前页
     * @param pageSize    页数量
     * @return List<FeatureCardVo>
     */
    PageResult<IFeatureVo> getFeatureCardVoList(FeatureCardVo searchValue, Integer current, Integer pageSize);

    /**
     * 保存卡信息
     *
     * @param cardModel cardModel
     * @return CardModel
     */
    CardModel saveCardModel(CardModel cardModel);

    /**
     * 删除卡信息
     *
     * @param cardIds cardIds
     */
    void deleteCardByIds(List<String> cardIds);

    /**
     * 校验数据
     *
     * @param cardModel cardModel
     */
    void validatorCardModel(CardModel cardModel);
}
