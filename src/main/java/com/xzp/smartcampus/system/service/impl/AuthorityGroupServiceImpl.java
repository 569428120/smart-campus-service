package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.mapper.AuthorityGroupMapper;
import com.xzp.smartcampus.system.mapper.RegionMapper;
import com.xzp.smartcampus.system.model.AuthorityGroupModel;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.service.IAuthorityGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AuthorityGroupServiceImpl extends IsolationBaseService<AuthorityGroupMapper, AuthorityGroupModel> implements IAuthorityGroupService {

    /**
     * 分页查询
     *
     * @param searchValue 查询条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return PageResult
     */
    @Override
    public PageResult getAuthorityGroupPage(AuthorityGroupModel searchValue, Integer current, Integer pageSize) {
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<AuthorityGroupModel>()
                .like(StringUtils.isNotBlank(searchValue.getAuthorityName()), "authority_name", searchValue.getAuthorityName())
                .like(StringUtils.isNotBlank(searchValue.getAuthorityCode()), "authority_code", searchValue.getAuthorityCode())
        );
    }

    /**
     * 保存权限组数据
     *
     * @param groupModel 数据
     * @return AuthorityGroupModel
     */
    @Override
    public AuthorityGroupModel saveAuthorityGroup(AuthorityGroupModel groupModel) {
        // 新增操作
        if (StringUtils.isBlank(groupModel.getId())) {
            groupModel.setId(SqlUtil.getUUId());
            this.insert(groupModel);
            return groupModel;
        }
        // 更新操作
        AuthorityGroupModel localDbGroupModel = this.selectById(groupModel.getId());
        if (localDbGroupModel == null) {
            log.error("not find AuthorityGroupModel by id {}", groupModel.getId());
            throw new SipException("数据错误，找不到AuthorityGroupModel id为 " + groupModel.getId());
        }
        localDbGroupModel.setAuthorityName(groupModel.getAuthorityName());
        localDbGroupModel.setAuthorityCode(groupModel.getAuthorityCode());
        localDbGroupModel.setDescription(groupModel.getDescription());
        return localDbGroupModel;
    }
}
