package com.xzp.smartcampus.authority.web;


import com.xzp.smartcampus.authority.param.BindStudentParam;
import com.xzp.smartcampus.authority.service.IBindStudentService;
import com.xzp.smartcampus.authority.vo.BindStudentVo;
import com.xzp.smartcampus.human.model.StudentModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/authority/bind-student")
public class BindStudentController {

    @Resource
    private IBindStudentService bindStudentService;

    @GetMapping("/gets/gets-by-cellNumber")
    public ResponseEntity<List<BindStudentVo>> getBindStudentList(@NotBlank(message = "手机号码不能为空") String cellNumber) {
        return ResponseEntity.ok(bindStudentService.getBindStudentList(cellNumber));
    }

    @PostMapping("/bind")
    public ResponseEntity<StudentModel> bindStudent(@RequestBody @Valid BindStudentParam paramVo) {
        return ResponseEntity.ok(bindStudentService.bindStudent(paramVo));
    }

    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteBindStudentByIds(@RequestParam(value = "cellNumber") String cellNumber, @RequestParam(value = "studentId") String studentId) {
        bindStudentService.deleteBindStudentByIds(cellNumber, studentId);
        return ResponseEntity.ok("操作成功");
    }
}
