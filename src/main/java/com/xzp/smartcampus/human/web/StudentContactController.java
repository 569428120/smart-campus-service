package com.xzp.smartcampus.human.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.StudentContactService;
import com.xzp.smartcampus.human.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/human/student/contact")
public class StudentContactController {

    @Resource
    private StudentContactService studentContactService;

    /* ********************** 员工的联系人 CRUD ********************** */

    /** 按照id 查询全部的联系人
     * @param id  对象id
     * */
    @GetMapping("/")
    public ResponseEntity<List<StudentContactModel>> getContactsById(@RequestParam(value = "id", defaultValue = "") String id){

        return ResponseEntity.ok(studentContactService.getContactsByStudentId(id));
    }

      /** 给指定id 的对象，增加一个联系人
       * */
      @PostMapping("/")
      public ResponseEntity<StudentContactModel> addContact(@RequestBody StudentContactModel contact){
          return ResponseEntity.ok(studentContactService.addContact(contact));
      }




}
