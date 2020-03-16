package com.xzp.smartcampus.system.web;


import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.model.SchoolModel;
import com.xzp.smartcampus.system.service.ISchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 学校接口
 */
@RestController
@RequestMapping("/school")
public class SchoolController {

    @Resource
    private ISchoolService schoolService;

    /**
     * 分页查询教育局数据
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getSchoolListPage(SchoolModel searchValue,
                                                        @RequestParam(value = "current") Integer current,
                                                        @RequestParam(value = "pageSize") Integer pageSize) {

        return ResponseEntity.ok(schoolService.getSchoolListPage(searchValue, current, pageSize));
    }


    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteSchoolByIds(@RequestParam(value = "schoolIds", defaultValue = "") String schoolIds) {
        schoolService.deleteByIds(Arrays.asList(schoolIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }


    /**
     * 修改或新增方法
     *
     * @param schoolModel 数据
     * @return ResponseEntity<RegionModel>
     */
    @PostMapping("/posts")
    public ResponseEntity<SchoolModel> postSchoolModel(@RequestBody SchoolModel schoolModel) {
        return ResponseEntity.ok(schoolService.postSchoolModel(schoolModel));
    }


}
