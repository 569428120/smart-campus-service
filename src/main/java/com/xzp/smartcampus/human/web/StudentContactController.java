package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.human.service.IStudentContactService;
import com.xzp.smartcampus.human.vo.StudentContactPostVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/human/student-contact")
public class StudentContactController {

    @Resource
    private IStudentContactService studentContactService;

    @PostMapping("/posts")
    public ResponseEntity<String> saveStudentContact(@RequestBody StudentContactPostVo postVo) {
        studentContactService.saveStudentContact(postVo.getContactList(), postVo.getStudentId());
        return ResponseEntity.ok("保存成功");
    }
}
