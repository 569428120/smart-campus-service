package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.StaffMapper;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffGroupService;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.human.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StaffServiceImpl extends IsolationBaseService<StaffMapper, StaffModel> implements IStaffUserService {

    @Resource
    private IStaffGroupService groupService;

    @Override
    public PageResult<UserVo> getUserVoListPage(StaffModel searchValue, Integer current, Integer pageSize) {
        PageResult<StaffModel> modelPage = this.getStaffModelPage(searchValue, current, pageSize);
        return new PageResult<>(modelPage.getTotal(), modelPage.getTotalPage(), this.toUserVoLList(modelPage.getData()));
    }

    private List<UserVo> toUserVoLList(List<StaffModel> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        Set<String> groupIds = data.stream().map(StaffModel::getGroupId).collect(Collectors.toSet());
        Map<String, StaffGroupModel> groupIdToModelMap = this.getGroupIdToModelMap(groupIds);
        return data.stream().map(item -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(item, userVo);
            if (groupIdToModelMap.containsKey(item.getGroupId())) {
                userVo.setGroupName(this.getTreePathNames(groupIdToModelMap.get(item.getGroupId()), groupIdToModelMap));
            }
            return userVo;
        }).collect(Collectors.toList());
    }

    /**
     * treepath转换名称
     *
     * @param groupModel        groupModel
     * @param groupIdToModelMap groupIdToModelMap
     * @return String
     */
    private String getTreePathNames(StaffGroupModel groupModel, Map<String, StaffGroupModel> groupIdToModelMap) {
        if (groupModel == null) {
            return "";
        }
        if (StringUtils.isBlank(groupModel.getTreePath())) {
            return "";
        }
        List<String> names = new ArrayList<>(3);
        Arrays.asList(groupModel.getTreePath().split(Constant.TREE_SEPARATOR)).forEach(id -> {
            if (Constant.ROOT.equals(id)) {
                return;
            }
            StaffGroupModel model = groupIdToModelMap.get(id);
            if (model == null) {
                names.add("已被删除");
                return;
            }
            names.add(model.getGroupName());
        });
        return String.join("/", names);
    }

    /**
     * id映射model
     *
     * @param groupIds groupIds
     * @return Map
     */
    private Map<String, StaffGroupModel> getGroupIdToModelMap(Collection<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return Collections.emptyMap();
        }
        List<StaffGroupModel> groupModels = groupService.selectByIds(groupIds);
        if (CollectionUtils.isEmpty(groupModels)) {
            return Collections.emptyMap();
        }
        List<String> treePaths = groupModels.stream().map(StaffGroupModel::getTreePath).collect(Collectors.toList());
        List<String> ids = new ArrayList<>(treePaths.size());
        treePaths.forEach(treePath -> {
            if (StringUtils.isBlank(treePath)) {
                return;
            }
            ids.addAll(Arrays.asList(treePath.split(Constant.TREE_SEPARATOR)));
        });

        List<StaffGroupModel> allModels = groupService.selectByIds(ids);
        if (CollectionUtils.isEmpty(allModels)) {
            return Collections.emptyMap();
        }
        return allModels.stream().collect(Collectors.toMap(StaffGroupModel::getId, v -> v));
    }

    /**
     * 分页查询model
     *
     * @param searchValue searchValue
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<StaffModel>
     */
    private PageResult<StaffModel> getStaffModelPage(StaffModel searchValue, Integer current, Integer pageSize) {
        List<String> groupIds = null;
        if (StringUtils.isNotBlank(searchValue.getGroupId()) && !Constant.ROOT.equals(searchValue.getGroupId())) {
            groupIds = Collections.singletonList("-1");
            StaffGroupModel groupModel = groupService.selectById(searchValue.getGroupId());
            if (groupModel != null) {
                List<StaffGroupModel> groupModels = groupService.selectList(new QueryWrapper<StaffGroupModel>()
                        .likeRight("tree_path", groupModel.getTreePath())
                );
                if (!CollectionUtils.isEmpty(groupModels)) {
                    groupIds = groupModels.stream().map(StaffGroupModel::getId).collect(Collectors.toList());
                }
            }
        }
        QueryWrapper<StaffModel> wrapper = new QueryWrapper<>();
        wrapper.in(!CollectionUtils.isEmpty(groupIds), "group_id", groupIds);
        wrapper.eq(StringUtils.isNotBlank(searchValue.getUserType()), "user_type", searchValue.getUserType());
        if (StringUtils.isNotBlank(searchValue.getUserIdentity())) {
            wrapper.and(qw -> qw.like("user_identity", searchValue.getUserIdentity())
                    .or()
                    .like("user_job_code", searchValue.getUserIdentity())
            );
        }
        wrapper.like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName());
        wrapper.orderByDesc("create_time");
        return this.selectPage(new Page<>(current, pageSize), wrapper);
    }

    /**
     * 保存数据
     *
     * @param staffModel 数据
     * @return StaffModel
     */
    @Override
    public StaffModel postStaffUserModel(StaffModel staffModel) {
        if (StringUtils.isBlank(staffModel.getUserName())) {
            staffModel.setUserName(staffModel.getContact());
        }
        if (StringUtils.isBlank(staffModel.getId())) {
            return this.insertStaffUserModel(staffModel);
        }
        return this.updateStaffUserModel(staffModel);
    }

    /**
     * 根据id获取用户vo
     *
     * @param userIds userIds
     * @return List<UserVo>
     */
    @Override
    public List<UserVo> getUserVoListByIds(List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        List<StaffModel> userModels = this.selectByIds(userIds);
        if (CollectionUtils.isEmpty(userModels)) {
            return Collections.emptyList();
        }
        return this.toUserVoLList(userModels);
    }

    /**
     * 更新用户 不能更新分组
     *
     * @param staffModel staffModel
     * @return StaffModel
     */
    private StaffModel updateStaffUserModel(StaffModel staffModel) {
        if (StringUtils.isBlank(staffModel.getId())) {
            log.warn("id is null");
            throw new SipException("数据错误，id不能为空");
        }
        StaffModel localModel = this.selectById(staffModel.getId());
        if (localModel == null) {
            log.warn("not find localModel by id {}", staffModel.getId());
            throw new SipException("参数错误，找不到需要更新的数据 id " + staffModel.getId());
        }
        localModel.setContact(staffModel.getContact());
        localModel.setUserPassword(staffModel.getUserPassword());
        localModel.setName(staffModel.getName());
        localModel.setUserIdentity(staffModel.getUserIdentity());
        localModel.setUserType(staffModel.getUserType());
        localModel.setAddress(staffModel.getAddress());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 新增数据
     *
     * @param staffModel 数据
     */
    private StaffModel insertStaffUserModel(StaffModel staffModel) {
        if (StringUtils.isBlank(staffModel.getGroupId())) {
            log.warn("groupId is null");
            throw new SipException("参数错误，groupId不能为空");
        }
        staffModel.setId(SqlUtil.getUUId());
        this.insert(staffModel);
        return staffModel;
    }
}
