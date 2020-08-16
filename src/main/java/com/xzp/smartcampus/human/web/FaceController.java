package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.FaceModel;
import com.xzp.smartcampus.human.service.IFeatureFaceService;
import com.xzp.smartcampus.human.vo.FeatureFaceVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController
@RequestMapping("/human/face")
public class FaceController {

    @Resource
    private IFeatureFaceService faceService;

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult<IFeatureVo>> getFeatureFaceVoList(FeatureFaceVo searchValue,
                                                                       @RequestParam(value = "current") Integer current,
                                                                       @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(faceService.getFeatureFaceVoList(searchValue, current, pageSize));
    }

    /**
     * 修改或新增方法
     *
     * @param faceModel 数据
     * @return ResponseEntity<StaffModel>
     */
    @PostMapping("/posts")
    public ResponseEntity<FaceModel> saveFaceModel(@RequestBody FaceModel faceModel) {
        return ResponseEntity.ok(faceService.saveFaceModel(faceModel));
    }

    /**
     * 校验
     *
     * @param faceModel faceModel
     * @return CardModel
     */
    @PostMapping("/validator")
    public ResponseEntity<String> validatorFaceModel(@RequestBody FaceModel faceModel) {
        try {
            faceService.validatorFaceModel(faceModel);
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
    public ResponseEntity<String> deleteFaceByIds(@RequestParam(value = "faceIds", defaultValue = "") String cardIds) {
        faceService.deleteFaceByIds(Arrays.asList(cardIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }
}
