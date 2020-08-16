package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.mobileapi.service.IMobileAppService;
import com.xzp.smartcampus.mobileapi.vo.AppInfo;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhipeng
 */
@RestController
@RequestMapping("/mobileApi/selected-app")
@Slf4j
public class SelectedAppApi {

    @Resource
    private IMobileAppService appService;

    /**
     * 获取在应用主页面选择的app
     *
     * @return List<AppInfo>
     */
    @GetMapping("/gets")
    public ResponseEntity<List<AppInfo>> getAuthorityGroupPage() {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        if (userInfo == null) {
            log.warn("not login user");
            throw new SipException("当前用户未登录");
        }
        return ResponseEntity.ok(appService.getSelectedAppListByUserId(userInfo.getUserId()));
    }

    /**
     * 选择app放在主页面
     *
     * @param appIds appIds
     * @return String
     */
    @PostMapping("/posts/posts-by-ids")
    public ResponseEntity<String> selectedApp(@RequestParam(value = "appIds") List<String> appIds) {
        // TODO 展示不做出来
        return ResponseEntity.ok("操作成功");
    }

    /**
     * 将app从主页移除
     *
     * @param appIds appIds
     * @return String
     */
    @DeleteMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteSelectedApp(@RequestParam(value = "appIds") List<String> appIds) {
        // TODO 展示不做出来
        return ResponseEntity.ok("操作成功");
    }


}
