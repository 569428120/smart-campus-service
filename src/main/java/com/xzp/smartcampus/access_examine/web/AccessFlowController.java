package com.xzp.smartcampus.access_examine.web;

import com.xzp.smartcampus.access_examine.service.IAccessFlowService;
import com.xzp.smartcampus.access_examine.vo.AccessExamineVo;
import com.xzp.smartcampus.access_examine.vo.ExamineFlowParam;
import com.xzp.smartcampus.access_examine.vo.ExamineSearchParam;
import com.xzp.smartcampus.access_examine.vo.ExamineSearchResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/access-flow")
public class AccessFlowController {
    @Resource
    IAccessFlowService flowService;

    /**
     * 保存电子流
     *
     * @param examineVo
     * @return
     */
    @PostMapping("/posts/save-flow")
    public ResponseEntity<String> saveAccessFlow(@RequestBody AccessExamineVo examineVo) {
        this.flowService.saveAccessFlow(examineVo);
        return ResponseEntity.ok("New access flow created successfully!");
    }

    /**
     *
     * @param id 流程业务id
     * @return
     */
    @PutMapping("/puts/commit-flow")
    public ResponseEntity<String> commitAccessFlow(@RequestParam(value = "id",required = true) String id){
        this.flowService.commitAccessFlow(id);
        return ResponseEntity.ok("New access flow commit successfully!");
    }

    /**
     * 删除电子流
     *
     * @param id  流程业务id
     * @return
     */
    @DeleteMapping("/deletes/flow/{id}")
    public ResponseEntity<String> deleteAccessFlow(@PathVariable(value = "id") String id) {
        this.flowService.deleteAccessFlow(id);
        return ResponseEntity.ok("Access flow deleted successfully!");
    }

    /**
     * 条件搜索出入电子流
     * @param searchParam
     * @return
     */
    @GetMapping("/gets/flow")
    public ResponseEntity<List<ExamineSearchResult>> selectAccessExamine(ExamineSearchParam searchParam) {
        List<ExamineSearchResult> examineResults = this.flowService.searchAccessExamine(searchParam);
        if (CollectionUtils.isEmpty(examineResults)) {
            examineResults = Collections.emptyList();
        }
        return ResponseEntity.ok(examineResults);
    }

    /**
     * 审批电子流
     * @param flowParam
     * @return
     */
    @PutMapping("/puts/examine")
    public ResponseEntity<String> examineAccessFlow(@RequestBody ExamineFlowParam flowParam) {
        this.flowService.examineAccessFlow(flowParam);
        return ResponseEntity.ok("Access flow examine successfully!");
    }









}
