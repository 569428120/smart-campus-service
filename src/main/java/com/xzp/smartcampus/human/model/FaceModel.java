package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.human.vo.FeatureFaceVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import com.xzp.smartcampus.human.vo.IModelToFeatureVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * @author xuzhipeng
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_feature_face")
public class FaceModel extends BaseModel implements IModelToFeatureVo {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 照片列表 逗号分隔
     */
    private String imageIds;

    /**
     * 描述
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;

    @Override
    public IFeatureVo toFeatureVo() {
        FeatureFaceVo featureFaceVo = new FeatureFaceVo();
        BeanUtils.copyProperties(this, featureFaceVo);
        return featureFaceVo;
    }
}
