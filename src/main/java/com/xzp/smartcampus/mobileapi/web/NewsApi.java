package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.service.INewsService;
import com.xzp.smartcampus.mobileapi.vo.GroupNewsVo;
import com.xzp.smartcampus.mobileapi.vo.NewsVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi")
public class NewsApi {

    @Resource
    private INewsService newsService;

    @GetMapping("/group-news/gets")
    public ResponseEntity<List<GroupNewsVo>> getGroupNewsList() {
        return ResponseEntity.ok(newsService.getGroupNewsList());
    }

    @GetMapping("/news/gets/gets-by-groupId")
    public ResponseEntity<PageResult<NewsVo>> getNewsListByGroupId(NewsVo vo, String groupId, Integer current, Integer pageSize) {
        return ResponseEntity.ok(newsService.getNewsPageByGroupId(groupId, vo, current, pageSize));
    }

    @GetMapping("/news/details/gets/gets-by-id")
    public ResponseEntity<NewsVo> getNewsDetailById(String newsId) {
        return ResponseEntity.ok(newsService.getNewsDetailById(newsId));
    }

}
