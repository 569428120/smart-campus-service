package com.xzp.smartcampus.human.web;


import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.service.ISelectUserService;
import com.xzp.smartcampus.human.vo.UserGroupTreeVo;
import com.xzp.smartcampus.human.vo.UserVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/human/select-user")
public class SelectUserController {

    @Resource
    private ISelectUserService userService;

    /**
     * 获取分组数据
     *
     * @param searchValue searchValue
     * @param userType    userType
     * @return List<UserGroupTreeVo>
     */
    @GetMapping("/group/gets/tree")
    public ResponseEntity<List<UserGroupTreeVo>> getUserGroupTreeList(UserGroupTreeVo searchValue,
                                                                      @RequestParam(value = "userType") String userType) {
        return ResponseEntity.ok(userService.getUserGroupTreeList(searchValue, userType));
    }

    /**
     * 根据用户类型分页查询用户
     *
     * @param searchValue searchValue
     * @param groupId     groupId
     * @param userType    userType
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<UserVo>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult<UserVo>> getUserList(UserVo searchValue,
                                                          @RequestParam(value = "groupId", defaultValue = "root") String groupId,
                                                          @RequestParam(value = "userType") String userType,
                                                          @RequestParam(value = "current") Integer current,
                                                          @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(userService.getUserList(searchValue, groupId, userType, current, pageSize));
    }

}
