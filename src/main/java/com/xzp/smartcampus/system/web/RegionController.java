package com.xzp.smartcampus.system.web;


import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.service.IRegionService;
import com.xzp.smartcampus.system.vo.RegionVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 教育局接口
 */
@RestController
@RequestMapping("/region")
public class RegionController {

    @Resource
    private IRegionService regionService;

    /**
     * 分页查询教育局数据
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getRegionListPage(RegionModel searchValue,
                                                        @RequestParam(value = "current") Integer current,
                                                        @RequestParam(value = "pageSize") Integer pageSize) {

        return ResponseEntity.ok(regionService.getRegionListPage(searchValue, current, pageSize));
    }


    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteRegionByIds(@RequestParam(value = "regionIds", defaultValue = "") String regionIds) {
        regionService.deleteByIds(Arrays.asList(regionIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }


    /**
     * 修改或新增方法
     *
     * @param regionVo 数据
     * @return ResponseEntity<RegionModel>
     */
    @PostMapping("/posts")
    public ResponseEntity<RegionModel> postRegionModel(@RequestBody RegionVo regionVo) {
        return ResponseEntity.ok(regionService.postRegionModel(regionVo));
    }


    @PostMapping("/validator")
    public ResponseEntity<String> validatorRegion(@RequestBody RegionVo regionVo) {
        try {
            regionService.validatorRegion(regionVo);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("");
    }
}
