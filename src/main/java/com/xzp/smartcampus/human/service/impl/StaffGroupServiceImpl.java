package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.DataUtil;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.utils.TreeUtil;
import com.xzp.smartcampus.human.mapper.StaffGroupMapper;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffGroupService;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.human.vo.UserGroupTreeVo;
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
public class StaffGroupServiceImpl extends IsolationBaseService<StaffGroupMapper, StaffGroupModel> implements IStaffGroupService {

    @Resource
    private IStaffUserService staffUserService;

    /**
     * 查询树节点
     *
     * @param searchValue searchValue
     * @return List<UserGroupTreeVo>
     */
    @Override
    public List<UserGroupTreeVo> getUserGroupTreeVoList(StaffGroupModel searchValue) {
        List<StaffGroupModel> groupModels = this.selectList(new QueryWrapper<StaffGroupModel>()
                .like(StringUtils.isNotBlank(searchValue.getGroupName()), "group_name", searchValue.getGroupName())
                .orderByAsc("create_time")
        );
        if (CollectionUtils.isEmpty(groupModels)) {
            return Collections.emptyList();
        }
        // 查询需要展示的但是未搜索到的数据
        List<String> pids = this.getNotFindPids(groupModels);
        if (!CollectionUtils.isEmpty(pids)) {
            List<StaffGroupModel> pModels = this.selectList(new QueryWrapper<StaffGroupModel>()
                    .in("id", pids)
                    .orderByAsc("create_time")
            );
            if (!CollectionUtils.isEmpty(pModels)) {
                groupModels.addAll(pModels);
            }
        }
        // 转为树结构
        return TreeUtil.modelToTreeVo(groupModels, new UserGroupTreeVo());
    }

    /**
     * 查询需要展示的但是未搜索到的数据
     *
     * @param groupModels groupModels
     * @return List<String>
     */
    @Override
    public List<String> getNotFindPids(List<StaffGroupModel> groupModels) {
        if (CollectionUtils.isEmpty(groupModels)) {
            return Collections.emptyList();
        }
        List<String> treePaths = groupModels.stream().map(StaffGroupModel::getTreePath).collect(Collectors.toList());
        List<String> ids = TreeUtil.treePathToIds(treePaths);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        Map<String, StaffGroupModel> idToModelMap = groupModels.stream().collect(Collectors.toMap(StaffGroupModel::getId, v -> v));
        return ids.stream().filter(id -> !idToModelMap.containsKey(id)).collect(Collectors.toList());
    }

    /**
     * 保存
     *
     * @param groupModel groupModel
     * @return StaffGroupModel
     */
    @Override
    public StaffGroupModel postGroupModel(StaffGroupModel groupModel) {
        if (StringUtils.isBlank(groupModel.getId())) {
            return this.createStaffGroupModel(groupModel);
        }
        return this.updateStaffGroupModel(groupModel);
    }

    /**
     * 更新 只更新名称
     *
     * @param groupModel groupModel
     * @return StaffGroupModel
     */
    private StaffGroupModel updateStaffGroupModel(StaffGroupModel groupModel) {
        if (StringUtils.isBlank(groupModel.getId())) {
            log.warn("id is null");
            throw new SipException("参数错误，更新操作id不能为空");
        }
        StaffGroupModel localModel = this.selectById(groupModel.getId());
        if (localModel == null) {
            log.warn("not find localModel by id {}", groupModel.getId());
            throw new SipException("参数错误，找不到更新对象 id " + groupModel.getId());
        }
        localModel.setAuthorityId(groupModel.getAuthorityId());
        localModel.setAccessStrategyId(groupModel.getAccessStrategyId());
        localModel.setGroupName(groupModel.getGroupName());
        localModel.setDescription(groupModel.getDescription());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 创建
     *
     * @param groupModel groupModel
     * @return StaffGroupModel
     */
    private StaffGroupModel createStaffGroupModel(StaffGroupModel groupModel) {
        groupModel.setId(SqlUtil.getUUId());
        groupModel.setGroupCode(groupModel.getId());
        if (StringUtils.isBlank(groupModel.getPid()) || Constant.ROOT.equals(groupModel.getPid())) {
            groupModel.setPid(Constant.ROOT);
            groupModel.setTreePath(Constant.ROOT + Constant.TREE_SEPARATOR + groupModel.getId());
        } else {
            StaffGroupModel pModel = this.selectById(groupModel.getPid());
            if (pModel == null) {
                log.warn("not find pModel by id " + groupModel.getPid());
                throw new SipException("参数错误，找不到父节点数据 id " + groupModel.getPid());
            }
            groupModel.setPid(pModel.getId());
            groupModel.setTreePath(pModel.getTreePath() + Constant.TREE_SEPARATOR + groupModel.getId());
        }
        this.insert(groupModel);
        return groupModel;
    }

    /**
     * 复制分组
     *
     * @param sourceIds sourceIds
     * @param targetIds targetIds
     */
    @Override
    public void copyGroupToGroups(List<String> sourceIds, List<String> targetIds) {
        if (CollectionUtils.isEmpty(sourceIds) || CollectionUtils.isEmpty(targetIds)) {
            log.info("sourceIds or targetIds is null");
            return;
        }
        List<StaffGroupModel> sourceGroupModels = this.selectByIds(sourceIds);
        if (CollectionUtils.isEmpty(sourceGroupModels)) {
            log.warn("not find sourceGroupModels by ids {}", sourceIds);
            return;
        }
        List<StaffGroupModel> sourceChildrenList = this.getChildrenList(sourceGroupModels);
        List<StaffGroupModel> targetGroupModels = this.selectByIds(targetIds);
        if (CollectionUtils.isEmpty(targetGroupModels)) {
            log.warn("not find targetGroupModels by ids {}", targetIds);
            return;
        }
        Map<String, StaffGroupModel> sourceIdAndPidToCopyModelMap = new LinkedHashMap<>(targetGroupModels.size() * (sourceGroupModels.size() + sourceChildrenList.size()));
        targetGroupModels.forEach(pModel -> {
            sourceGroupModels.forEach(groupModel -> {
                StaffGroupModel newModel = new StaffGroupModel();
                BeanUtils.copyProperties(groupModel, newModel);
                newModel.setId(SqlUtil.getUUId());
                newModel.setGroupCode(newModel.getId());
                newModel.setPid(pModel.getId());
                newModel.setTreePath(pModel.getTreePath() + Constant.TREE_SEPARATOR + newModel.getId());
                sourceIdAndPidToCopyModelMap.put(groupModel.getId() + pModel.getId(), newModel);
            });
        });
        // 安装treePath的长度排序
        sourceChildrenList.sort(Comparator.comparingInt(o -> DataUtil.getTreePathNumber(o.getTreePath())));
        targetGroupModels.forEach(sModel -> {
            sourceChildrenList.forEach(item -> {
                StaffGroupModel pModel = sourceIdAndPidToCopyModelMap.get(item.getPid() + sModel.getId());
                if (pModel == null) {
                    log.error("data error,not find in sourceIdAndPidToCopyModelMap by sourceId {} pid {}", item.getPid(), sModel.getId());
                    throw new SipException("数据错误，找不到原始父节点 sourceId " + item.getPid() + " pid " + sModel.getId());
                }
                StaffGroupModel newModel = new StaffGroupModel();
                BeanUtils.copyProperties(item, newModel);
                newModel.setId(SqlUtil.getUUId());
                newModel.setPid(pModel.getId());
                newModel.setTreePath(pModel.getTreePath() + Constant.TREE_SEPARATOR + newModel.getId());
                sourceIdAndPidToCopyModelMap.put(item.getId() + sModel.getId(), newModel);
            });
        });
        // 保存
        if (!CollectionUtils.isEmpty(sourceIdAndPidToCopyModelMap)) {
            this.insertBatch(sourceIdAndPidToCopyModelMap.values());
        }
    }

    /**
     * 获取分组子子节点数据
     *
     * @param groupModels groupIds
     * @return List<StaffGroupModel>
     */
    private List<StaffGroupModel> getChildrenList(List<StaffGroupModel> groupModels) {
        if (CollectionUtils.isEmpty(groupModels)) {
            log.info("groupModels is null");
            return Collections.emptyList();
        }
        List<String> ids = groupModels.stream().map(StaffGroupModel::getId).collect(Collectors.toList());
        QueryWrapper<StaffGroupModel> wrapper = new QueryWrapper<>();
        wrapper.notIn("id", ids);
        wrapper.and((qw) -> {
            groupModels.forEach(item -> {
                qw.or();
                qw.like("tree_path", item.getTreePath());
            });
            return qw;
        });
        List<StaffGroupModel> children = this.selectList(wrapper);
        return CollectionUtils.isEmpty(children) ? Collections.emptyList() : children;
    }

    /**
     * 移动分组
     *
     * @param sourceIds sourceIds
     * @param targetId  targetId
     */
    @Override
    public void moveGroupToGroups(List<String> sourceIds, String targetId) {
        if (CollectionUtils.isEmpty(sourceIds) || StringUtils.isBlank(targetId)) {
            log.info("sourceIds or targetId is null");
            return;
        }
        List<StaffGroupModel> sourceGroupModels = this.selectByIds(sourceIds);
        if (CollectionUtils.isEmpty(sourceGroupModels)) {
            log.warn("not find sourceGroupModels by ids {}", sourceIds);
            return;
        }
        List<StaffGroupModel> sourceChildrenList = this.getChildrenList(sourceGroupModels);
        StaffGroupModel targetGroupModel = this.selectById(targetId);
        if (targetGroupModel == null) {
            log.warn("not find targetGroupModel by id {}", targetId);
            return;
        }
        Map<String, StaffGroupModel> idToMoveModelMap = new LinkedHashMap<>(sourceGroupModels.size() + sourceChildrenList.size());
        sourceGroupModels.forEach(item -> {
            item.setPid(targetGroupModel.getId());
            item.setTreePath(targetGroupModel.getTreePath() + Constant.TREE_SEPARATOR + item.getId());
            idToMoveModelMap.put(item.getId(), item);
        });
        sourceChildrenList.sort(Comparator.comparingInt(o -> DataUtil.getTreePathNumber(o.getTreePath())));
        sourceChildrenList.forEach(item -> {
            StaffGroupModel pModel = idToMoveModelMap.get(item.getPid());
            if (pModel == null) {
                log.error("data error,not find in idToMoveModelMap by id {}", item.getPid());
                throw new SipException("数据错误，找不到原始父节点 id " + item.getPid());
            }
            item.setPid(pModel.getId());
            item.setTreePath(pModel.getTreePath() + Constant.TREE_SEPARATOR + item.getId());
            idToMoveModelMap.put(item.getId(), item);
        });
        // 保存
        if (!CollectionUtils.isEmpty(idToMoveModelMap)) {
            this.updateBatch(idToMoveModelMap.values());
        }
    }

    /**
     * 移动用户到分组
     *
     * @param userIds  userIds
     * @param targetId targetId
     */
    @Override
    public void moveUserToGroups(List<String> userIds, String targetId) {
        if (CollectionUtils.isEmpty(userIds) || StringUtils.isBlank(targetId)) {
            log.warn("userIds or targetId is null");
            return;
        }
        List<StaffModel> userModels = staffUserService.selectByIds(userIds);
        if (CollectionUtils.isEmpty(userModels)) {
            log.warn("not find userModels by ids {}", userIds);
            return;
        }
        StaffGroupModel targetGroupModel = this.selectById(targetId);
        if (targetGroupModel == null) {
            log.warn("not find targetGroupModel by id {}", targetId);
            return;
        }
        userModels.forEach(item -> {
            item.setGroupId(targetGroupModel.getId());
        });
        staffUserService.updateBatch(userModels);
    }

    /**
     * 删除分组
     *
     * @param groupIds groupIds
     */
    @Override
    public void deleteGroupByIds(List<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            log.warn("groupIds is null");
            return;
        }
        QueryWrapper<StaffGroupModel> wrapper = new QueryWrapper<>();
        for (String id : groupIds) {
            wrapper.or();
            wrapper.like("tree_path", id);
        }
        List<StaffGroupModel> groupModels = this.selectList(wrapper);
        if (CollectionUtils.isEmpty(groupModels)) {
            return;
        }
        List<String> ids = groupModels.stream().map(StaffGroupModel::getId).collect(Collectors.toList());
        // 删除关联的用户
        staffUserService.delete(new UpdateWrapper<StaffModel>()
                .in("group_id", ids)
        );
        this.deleteByIds(ids);
    }
}
