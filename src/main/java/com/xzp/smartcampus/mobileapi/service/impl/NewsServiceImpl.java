package com.xzp.smartcampus.mobileapi.service.impl;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.service.INewsService;
import com.xzp.smartcampus.mobileapi.vo.GroupNewsVo;
import com.xzp.smartcampus.mobileapi.vo.NewsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class NewsServiceImpl implements INewsService {

    /**
     * 查询新闻分组
     *
     * @return getGroupNewsList
     */
    @Override
    public List<GroupNewsVo> getGroupNewsList() {

        return Stream.of("校内新闻", "校园通知").map(name -> {
            GroupNewsVo groupNewsVo = new GroupNewsVo();
            groupNewsVo.setId(name);
            groupNewsVo.setGroupName(name);
            groupNewsVo.setNews(this.getNewsVoListByGroupId(groupNewsVo.getId(), false));
            return groupNewsVo;
        }).collect(Collectors.toList());
    }

    /**
     * 分页查询
     *
     * @param groupId  分组id
     * @param vo       搜索条件
     * @param current  当前页
     * @param pageSize 也容量
     * @return PageResult<NewsVo>
     */
    @Override
    public PageResult<NewsVo> getNewsPageByGroupId(String groupId, NewsVo vo, Integer current, Integer pageSize) {
        if (StringUtils.isBlank(groupId) || current == null || pageSize == null) {
            log.warn("groupId or current or pageSize is null");
            throw new SipException("groupId、current、pageSize 不能为空");
        }
        List<NewsVo> newsVos = this.getNewsVoListByGroupId(groupId, false);
        return new PageResult<NewsVo>((long) newsVos.size(), newsVos);
    }

    /**
     * 查询新闻详情
     *
     * @param newsId newsId
     * @return NewsVo
     */
    @Override
    public NewsVo getNewsDetailById(String newsId) {
        if (StringUtils.isBlank(newsId)) {
            log.warn("newsId is null");
            throw new SipException("newsId 不能为空");
        }
        List<NewsVo> newsVos = this.getNewsVoListByGroupId(newsId, true);
        return newsVos.get(0);
    }

    /**
     * 新闻列表
     *
     * @param groupId groupId
     * @return List<NewsVo>
     */
    private List<NewsVo> getNewsVoListByGroupId(String groupId, boolean isDetail) {

        return Stream.of("https://scs-image.oss-cn-beijing.aliyuncs.com/test-image/Hydrangeas.jpg", "https://scs-image.oss-cn-beijing.aliyuncs.com/test-image/Jellyfish.jpg").map(url -> {
            NewsVo newsVo = new NewsVo();
            newsVo.setId(SqlUtil.getUUId());
            newsVo.setGroupId(groupId);
            newsVo.setTitle("测试新闻");
            newsVo.setSubtitle("这个是测试新闻的子标题");
            newsVo.setTitleImageUrl(url);
            newsVo.setPublishedTime(new Date());
            newsVo.setVisits(500L);
            if (isDetail) {
                newsVo.setDetails("测试新闻的内容。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            }
            return newsVo;
        }).collect(Collectors.toList());
    }

}
