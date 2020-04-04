package com.xzp.smartcampus.human.web;

import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.human.model.FuckTestModel;
import com.xzp.smartcampus.human.service.FuckTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Random;

@RestController
@RequestMapping("/Test")
public class TestWeb {

    @Resource
    private FuckTestService fuckTestService;

    @GetMapping("/hello")
    public String hello(){ return "Hello beautiful world!/r/n"; }

    @GetMapping("/one")
    public ResponseEntity<String> getOne() {
        // 任意选取一个
        List queryList = fuckTestService.selectList(new QueryWrapper<FuckTestModel>());
        String result = "empty";
        if (! queryList.isEmpty()) {
            Random select = new Random();
            result = queryList.get(select.nextInt(queryList.size())).toString();
        }


        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteById (@RequestParam(value = "id", defaultValue = "") String id) {
        // 按照 id 选取一个
        FuckTestModel testModel = fuckTestService.selectById(id);
        if (testModel == null) {
            return ResponseEntity.ok("id invilid");
        }
        fuckTestService.deleteById(id);
        return ResponseEntity.ok("ok");
    }





}
