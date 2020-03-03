package com.xzp.smartcampus.test.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.test.model.TestModel;
import com.xzp.smartcampus.test.service.ITestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 测试
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ITestService testService;

    /**
     * 获取数据
     *
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/all")
    public ResponseEntity<List<TestModel>> getListPage(@RequestParam(value = "projectId") Long projectId,
                                                       @RequestParam(value = "name", defaultValue = "") String name) {

        return ResponseEntity.ok(testService.selectList(new QueryWrapper<>()));
    }


}
