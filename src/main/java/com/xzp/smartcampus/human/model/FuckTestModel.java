package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data  //
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_test")
public class FuckTestModel extends BaseModel {
/*CREATE TABLE `tb_test` (
  `id` char(32) NOT NULL,
  `region_id` char(32) NOT NULL,
  `school_id` char(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `logic_del` tinyint(2) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `age` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
* */
    private String region_id;
    private String school_id;
    private Date create_time;
    private Date update_time;
    private int logic_del;
    private String name;
    private int age;
}
