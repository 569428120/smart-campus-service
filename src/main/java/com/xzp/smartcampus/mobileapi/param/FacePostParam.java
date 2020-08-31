package com.xzp.smartcampus.mobileapi.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class FacePostParam {
    private String id;
    @NotNull(message = "userId 不能为空")
    private String userId;
    private List<String> imageIds;
}
