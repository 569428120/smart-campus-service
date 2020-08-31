package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import com.xzp.smartcampus.mobileapi.param.FacePostParam;
import com.xzp.smartcampus.mobileapi.param.FaceQueryParam;
import com.xzp.smartcampus.mobileapi.service.IFaceManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi/face-manage")
@Slf4j
public class FaceManageApi {

    @Resource
    private IFaceManageService faceManageService;

    /**
     * 获取当前用户的人脸信息列表
     *
     * @return FaceVo
     */
    @GetMapping("my-face/gets")
    public ResponseEntity<List<IFeatureVo>> getMyFaceList() {
        return ResponseEntity.ok(faceManageService.getMyFaceList());
    }

    /**
     * 根据人员id获取脸部图片
     *
     * @param userId 用户id
     * @return FaceVo
     */
    @GetMapping("/gets/gets-by-userId")
    public ResponseEntity<IFeatureVo> getFaceVoByUserId(@NotNull(message = "userId 不能空") String userId) {
        return ResponseEntity.ok(faceManageService.getFaceVoByUserId(userId));
    }

    /**
     * 分页查询
     *
     * @param queryParam 参数
     * @return 数据
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult<IFeatureVo>> getFaceVoPage(@Valid FaceQueryParam queryParam,
                                                                @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                                @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        return ResponseEntity.ok(faceManageService.getFaceVoPage(queryParam, current, pageSize));
    }

    /**
     * 保存脸部数据
     *
     * @param postParam 提交信息
     * @return String
     */
    @PostMapping("/posts")
    public ResponseEntity<String> saveFaceData(@Valid FacePostParam postParam) {
        faceManageService.saveFaceData(postParam);
        return ResponseEntity.ok("操作成功");
    }

    /**
     * 删除脸部数据
     *
     * @param faceIds faceIds
     * @return String
     */
    @DeleteMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteFaceByIds(@RequestParam(value = "faceIds") List<String> faceIds) {
        faceManageService.deleteFaceByIds(faceIds);
        return ResponseEntity.ok("操作成功");
    }

}
