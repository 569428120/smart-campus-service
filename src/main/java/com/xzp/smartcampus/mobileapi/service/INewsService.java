package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.vo.GroupNewsVo;
import com.xzp.smartcampus.mobileapi.vo.NewsVo;

import java.util.List;

/**
 * @author SGS
 */
public interface INewsService {

    /**
     * 查询新闻分组
     *
     * @return getGroupNewsList
     */
    List<GroupNewsVo> getGroupNewsList();

    /**
     * 分页查询
     *
     * @param groupId  分组id
     * @param vo       搜索条件
     * @param current  当前页
     * @param pageSize 也容量
     * @return PageResult<NewsVo>
     */
    PageResult<NewsVo> getNewsPageByGroupId(String groupId, NewsVo vo, Integer current, Integer pageSize);

    /**
     * 查询新闻详情
     *
     * @param newsId newsId
     * @return NewsVo
     */
    NewsVo getNewsDetailById(String newsId);
}
