package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/*CREATE TABLE `tb_human_ser_staff_contact` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
  `school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',
  `type` varchar(32) NOT NULL COMMENT '用户，学生家长，等',
  `user_id` varchar(64) NOT NULL COMMENT '职工id',
  `mode` varchar(32) NOT NULL COMMENT '通讯方式，手机，微信等',
  `number` varchar(128) NOT NULL COMMENT '号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员联系方式表';
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_staff_contact")
public class StaffContactModel extends BaseModel {
    // 这些字段在 BaseModel 里
    // `id` varchar(32) NOT NULL COMMENT 'id', 自动增长
    //`region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
    //`school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
    //`create_time` datetime DEFAULT NULL COMMENT '创建时间',
    //`update_time` datetime DEFAULT NULL COMMENT '更新时间',
    //`logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',

    //`type` varchar(32) NOT NULL COMMENT '用户，学生家长，等',
    private String type;

    //`user_id` varchar(64) NOT NULL COMMENT '职工id',
    private String userId;

    //`mode` varchar(32) NOT NULL COMMENT '通讯方式，手机，微信等',
    private String mode;

    //`number` varchar(128) NOT NULL COMMENT '号码',
    private String number;

}
