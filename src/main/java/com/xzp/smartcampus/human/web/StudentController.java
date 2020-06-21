package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.IStudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController
@RequestMapping("/human/student")
public class StudentController {

    @Resource
    private IStudentService studentService;

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页数量
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getStudentVoListPage(StudentModel searchValue,
                                                           @RequestParam(value = "current") Integer current,
                                                           @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(studentService.getStudentVoListPage(searchValue, current, pageSize));
    }

    /**
     * 修改或新增方法
     *
     * @param studentModel 数据
     * @return ResponseEntity<StaffModel>
     */
    @PostMapping("/posts")
    public ResponseEntity<StudentModel> postStudentModel(@RequestBody StudentModel studentModel) {
        return ResponseEntity.ok(studentService.postStudentModel(studentModel));
    }

    /**
     * 校验数据
     *
     * @param studentModel studentModel
     * @return String
     */
    @PostMapping("/validator")
    public ResponseEntity<String> validatorStudentModel(@RequestBody StudentModel studentModel) {
        try {
            studentService.validatorStudentModel(studentModel);
        } catch (SipException e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    /**
     * @param studentIds studentIds
     * @return ResponseEntity<String>
     */
    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteBatchByIds(@RequestParam(value = "studentIds", defaultValue = "") String studentIds) {
        studentService.deleteByIds(Arrays.asList(studentIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }

}
