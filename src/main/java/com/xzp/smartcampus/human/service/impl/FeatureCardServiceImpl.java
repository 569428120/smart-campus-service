package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.CardMapper;
import com.xzp.smartcampus.human.model.CardModel;
import com.xzp.smartcampus.human.service.IFeatureCardService;
import com.xzp.smartcampus.human.service.ISelectUserService;
import com.xzp.smartcampus.human.vo.FeatureCardVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FeatureCardServiceImpl extends IsolationBaseService<CardMapper, CardModel> implements IFeatureCardService {

    @Resource
    private ISelectUserService selectUserService;

    /**
     * 分页查询
     *
     * @param searchValue searchValue
     * @param current     当前页
     * @param pageSize    也数量
     * @return List<FeatureCardVo>
     */
    @Override
    public PageResult<IFeatureVo> getFeatureCardVoList(FeatureCardVo searchValue, Integer current, Integer pageSize) {
        // 为了确保性能，必须传入用户类型
        if (StringUtils.isBlank(searchValue.getUserType())) {
            log.warn("userType is null");
            return PageResult.emptyPageResult();
        }
        List<String> userIds = selectUserService.getUserIds(searchValue);
        PageResult<CardModel> pageResult = this.getCardModelPage(searchValue, userIds, current, pageSize);
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), selectUserService.toFeatureCardVoList(pageResult.getData()));
    }

    /**
     * 分页查询model
     *
     * @param searchValue searchValue
     * @param userIds     userIds
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult
     */
    private PageResult<CardModel> getCardModelPage(FeatureCardVo searchValue, List<String> userIds, Integer current, Integer pageSize) {
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<CardModel>()
                .in(!CollectionUtils.isEmpty(userIds), "user_id", userIds)
                .like(StringUtils.isNotBlank(searchValue.getCardNumber()), "card_number", searchValue.getCardNumber())
                .orderByDesc("create_time")
        );
    }

    /**
     * 保存卡信息
     *
     * @param cardModel cardModel
     * @return CardModel
     */
    @Override
    public CardModel saveCardModel(CardModel cardModel) {
        // 校验
        this.validatorCardModel(cardModel);
        // 创建
        if (StringUtils.isBlank(cardModel.getId())) {
            return this.createCardModel(cardModel);
        }
        // 更新
        return this.updateCardModel(cardModel);
    }

    /**
     * 更新
     *
     * @param cardModel cardModel
     * @return CardModel
     */
    private CardModel updateCardModel(CardModel cardModel) {
        if (StringUtils.isBlank(cardModel.getId())) {
            log.warn("user id is null");
            throw new SipException("参数错误，id为空");
        }
        CardModel localModel = this.selectById(cardModel.getId());
        localModel.setCardNumber(cardModel.getCardNumber());
        localModel.setCardType(cardModel.getCardType());
        localModel.setDescription(cardModel.getDescription());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 创建
     *
     * @param cardModel cardModel
     * @return CardModel
     */
    private CardModel createCardModel(CardModel cardModel) {
        if (StringUtils.isBlank(cardModel.getUserId())) {
            log.warn("user id is null");
            throw new SipException("参数错误，用户id不能为空");
        }
        cardModel.setId(SqlUtil.getUUId());
        this.insert(cardModel);
        return cardModel;
    }


    /**
     * 删除卡信息
     *
     * @param cardIds cardIds
     */
    @Override
    public void deleteCardByIds(List<String> cardIds) {
        if (CollectionUtils.isEmpty(cardIds)) {
            log.warn("cardIds is null");
            return;
        }
        this.deleteByIds(cardIds);
    }

    /**
     * 校验数据
     *
     * @param cardModel cardModel
     */
    @Override
    public void validatorCardModel(CardModel cardModel) {
        if (StringUtils.isNotBlank(cardModel.getCardNumber())) {
            List<CardModel> cardModels = this.selectList(new QueryWrapper<CardModel>()
                    .eq("card_number", cardModel.getCardNumber())
                    .notIn(StringUtils.isNotBlank(cardModel.getId()), "id", cardModel.getId())
            );
            if (!CollectionUtils.isEmpty(cardModels)) {
                log.warn("card_number {} exist", cardModel.getCardNumber());
                throw new SipException("参数错误，卡号已存在 " + cardModel.getCardNumber());
            }
        }
    }
}
