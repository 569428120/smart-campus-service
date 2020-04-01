package com.xzp.smartcampus.human.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.human.model.FuckTestModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.FuckTestService;
import com.xzp.smartcampus.human.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/human/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    /* 单个学生的CRUD */
    //按照学生号，获取一个学生的信息
    @GetMapping("/")
    public ResponseEntity<StudentModel> queryByid(@RequestParam(value = "id", defaultValue = "") String id){
        StudentModel student = studentService.selectById(id);
        return ResponseEntity.ok(student);
    }
    // 新建一个学生的信息
    @PostMapping("/")
    public ResponseEntity<StudentModel> addStudent(@RequestBody StudentModel studentModel){
        return ResponseEntity.ok(studentService.addStudent(studentModel));
    }
    // 改变一个学生的信息
    @PutMapping("/")
    @PatchMapping("/")
    public ResponseEntity<StudentModel> changeByid(@RequestBody StudentModel studentModel){
        return ResponseEntity.ok(studentService.changeStudent(studentModel));
    }
    // 删除一个学生的信息
    @DeleteMapping("/")
    public ResponseEntity<String> deleteByid(@RequestParam(value = "id", defaultValue = "") String id){
        StudentModel student = studentService.selectById(id);
        if (student == null){
            return ResponseEntity.ok("该学生信息不存在");
        }
        studentService.deleteById(id);
        return ResponseEntity.ok("");
    }

    /* 学生 批量 CRUD 暂时不需要 */

}
