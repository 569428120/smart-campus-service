package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.service.StudentGroupService;
import com.xzp.smartcampus.human.service.StudentToGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/human/studentgroup")
public class StudentGroupController {

    @Resource
    private StudentGroupService groupService;
    @Resource
    private StudentToGroupService groupMenberService;

    /* 组级别的操作 */
    //按照学生号，获取一个学生组的信息
    @GetMapping("/")
    public ResponseEntity<StudentGroupModel> queryByid(@RequestParam(value = "gid", defaultValue = "") String gid){
        return ResponseEntity.ok(groupService.selectById(gid));
    }
    // 新建一个学生组的信息
    @PostMapping("/")
    public ResponseEntity<StudentGroupModel> addStudent(@RequestBody StudentGroupModel group){
        return ResponseEntity.ok(groupService.addStudentGroup(group));
    }
    // 改变一个学生组的信息
    @PutMapping("/")
    @PatchMapping("/")
    public ResponseEntity<StudentGroupModel> changeBySid(@RequestBody StudentGroupModel group){
        return ResponseEntity.ok(groupService.changeStudentGroup(group));
    }
    // 删除一个学生组的信息
    @DeleteMapping("/")
    public ResponseEntity<String> deleteBySid(@RequestParam(value = "id", defaultValue = "") String id){
        StudentGroupModel group = groupService.selectById(id);
        if (group == null){
            return ResponseEntity.ok("该组的信息不存在");
        }
        groupService.deleteById(id);
        return ResponseEntity.ok("");
    }

    /* 组内成员的操作 */
    // 添加一个成员
    @PostMapping("/{gid}")
    public ResponseEntity<String> addMenber(@PathVariable("gid") String gid, @RequestParam(value = "member", defaultValue = "") String menber){
        boolean result = groupMenberService.putMenberIntogroup(gid, menber);
        if (result){
            return ResponseEntity.ok("");
        }
            return ResponseEntity.ok("添加失败");
    }
    // 删除一个成员
    @DeleteMapping("/{gid}")
    public ResponseEntity<String> delMenber(@PathVariable("gid") String gid, @RequestParam(value = "member", defaultValue = "") String menber){
        boolean result = groupMenberService.removeMenberOutgroup(gid, menber);
        if (result){
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok("添加失败");
    }



}
