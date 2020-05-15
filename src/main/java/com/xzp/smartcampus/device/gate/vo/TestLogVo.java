package com.xzp.smartcampus.device.gate.vo;

import com.xzp.smartcampus.device.gate.model.RunLogModel;
import com.xzp.smartcampus.device.gate.model.TestLogModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TestLogVo extends TestLogModel {

    /**
     * log信息
     */
    private List<RunLogModel> logList;

}
