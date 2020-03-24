package com.xzp.smartcampus.system.web;

import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.system.service.IAuthorityGroupToMenuService;
import com.xzp.smartcampus.system.vo.MenuTreeVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/authority-to-menu")
public class AuthorityGroupToMenuController {

    @Resource
    private IAuthorityGroupToMenuService groupToMenuService;

    /**
     * 分页查询教育局数据
     *
     * @param groupId 权限组id
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/authority-to-menu/gets/gets-by-groupid")
    public ResponseEntity<List<MenuTreeVo>> getMenuListByGroupId(@RequestParam(value = "groupId") String groupId) {
        return ResponseEntity.ok(groupToMenuService.getMenuListByGroupId(groupId));
    }

    /**
     * 保存数据
     *
     * @param groupId 分组id
     * @param menuIds 菜单ids
     * @return ResponseEntity<Void>
     */
    @PostMapping("/posts/post-by-groupid")
    public ResponseEntity<String> postGroupToMenusByIds(@RequestParam(value = "groupId") String groupId, @RequestParam(value = "menuIds") String menuIds) {
        groupToMenuService.postGroupToMenusByIds(groupId, Arrays.asList(menuIds.split(Constant.PARAMS_ID_SEPARATOR)));
        return ResponseEntity.ok("保存成功");
    }
}
