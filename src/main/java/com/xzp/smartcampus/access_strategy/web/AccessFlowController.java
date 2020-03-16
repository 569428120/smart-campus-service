package com.xzp.smartcampus.access_strategy.web;

import com.xzp.smartcampus.access_strategy.service.IAccessFlowService;
import com.xzp.smartcampus.access_strategy.vo.AccessExamineVo;
import com.xzp.smartcampus.access_strategy.vo.ExamineSearchParam;
import com.xzp.smartcampus.access_strategy.vo.ExamineSearchResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/access-flow")
public class AccessFlowController {
    @Resource
    IAccessFlowService flowService;

    /**
     * 新增出入电子流
     * @param examineVo
     * @return
     */
    @PostMapping("/posts/flow")
    public ResponseEntity<String> createAccessFlow(@RequestBody AccessExamineVo examineVo){
        this.flowService.createAccessFlow(examineVo);
        return ResponseEntity.ok("New access flow created successfully!");
    }

    /**
     * 删除电子流
     * @param id
     * @return
     */
    @DeleteMapping("/deletes/flow/{id}")
    public ResponseEntity<String> deleteAccessFlow(@PathVariable(value = "id") String id){
        this.flowService.deleteAccessFlow(id);
        return ResponseEntity.ok("Access flow deleted successfully!");
    }

    @GetMapping("/gets/flow")
    public ResponseEntity<List<ExamineSearchResult>> selectAccessExamine(ExamineSearchParam searchParam){
        List<>=this.flowService.searchAccessExamine();

    }
















}
