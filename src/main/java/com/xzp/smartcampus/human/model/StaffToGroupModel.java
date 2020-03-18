package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/*CREATE TABLE `tb_human_ser_staff_to_group` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
  `school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',
  `group_id` varchar(64) NOT NULL COMMENT '分组id',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职工分组和职工关系表';
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_staff_to_group")
public class StaffToGroupModel extends BaseModel {
    // 这些字段在 BaseModel 里
    // `id` varchar(32) NOT NULL COMMENT 'id', 自动增长
    //`region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
    //`school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
    //`create_time` datetime DEFAULT NULL COMMENT '创建时间',
    //`update_time` datetime DEFAULT NULL COMMENT '更新时间',
    //`logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',

    //`group_id` varchar(64) NOT NULL COMMENT '分组id',
    private String group_id;

    //`user_id` varchar(64) NOT NULL COMMENT '用户id',
    private String user_id;

}
