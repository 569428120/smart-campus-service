package com.xzp.smartcampus.human.web;


import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.CardModel;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.service.IFeatureCardService;
import com.xzp.smartcampus.human.vo.FeatureCardVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import com.xzp.smartcampus.human.vo.UserGroupTreeVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


/**
 * @author xuzhipeng
 */
@RestController
@RequestMapping("/human/card")
public class CardController {

    @Resource
    private IFeatureCardService cardService;

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult<IFeatureVo>> getFeatureCardVoList(FeatureCardVo searchValue,
                                                                       @RequestParam(value = "current") Integer current,
                                                                       @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(cardService.getFeatureCardVoList(searchValue, current, pageSize));
    }

    /**
     * 修改或新增方法
     *
     * @param cardModel 数据
     * @return ResponseEntity<StaffModel>
     */
    @PostMapping("/posts")
    public ResponseEntity<CardModel> saveCardModel(@RequestBody CardModel cardModel) {
        return ResponseEntity.ok(cardService.saveCardModel(cardModel));
    }

    /**
     * 校验
     *
     * @param cardModel cardModel
     * @return CardModel
     */
    @PostMapping("/validator")
    public ResponseEntity<String> validatorCardModel(@RequestBody CardModel cardModel) {
        try {
            cardService.validatorCardModel(cardModel);
        } catch (SipException e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    /**
     * 删除数据
     *
     * @param cardIds cardIds
     * @return ResponseEntity<String>
     */
    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteCardByIds(@RequestParam(value = "cardIds", defaultValue = "") String cardIds) {
        cardService.deleteCardByIds(Arrays.asList(cardIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }
}
