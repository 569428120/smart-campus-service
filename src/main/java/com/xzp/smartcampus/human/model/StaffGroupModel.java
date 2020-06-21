package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/*CREATE TABLE `tb_human_ser_staff_group` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
  `school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',
  `pid` varchar(32) DEFAULT NULL COMMENT '父节点id',
  `tree_path` varchar(255) DEFAULT NULL COMMENT '关联关系',
  `group_name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `group_code` varchar(64) DEFAULT NULL COMMENT '分组编码',
  `authority_id` varchar(64) DEFAULT NULL COMMENT '权限id',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职工分组';
*/

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_staff_group")
public class StaffGroupModel extends BaseModel {
    // 这些字段在 BaseModel 里
    // `id` varchar(32) NOT NULL COMMENT 'id', 自动增长
    //`region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
    //`school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
    //`create_time` datetime DEFAULT NULL COMMENT '创建时间',
    //`update_time` datetime DEFAULT NULL COMMENT '更新时间',
    //`logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',

    //`pid` varchar(32) DEFAULT NULL COMMENT '父节点id',
    private String pid;

    //`tree_path` varchar(255) DEFAULT NULL COMMENT '结构关系',
    private String treePath;

    //`group_name` varchar(64) DEFAULT NULL COMMENT '班级或者分组名称',
    private String groupName;

    //`group_code` varchar(32) DEFAULT NULL COMMENT '分组编码',
    private String groupCode;

    //`authority_id` varchar(32) DEFAULT NULL COMMENT '权限组id',
    private String authorityId;

    /**
     * 门禁策略id
     */
    private String accessStrategyId;

    //`description` varchar(255) DEFAULT NULL COMMENT '描述',
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;


}
