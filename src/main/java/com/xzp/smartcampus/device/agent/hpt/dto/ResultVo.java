package com.xzp.smartcampus.device.agent.hpt.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ResultVo {
    private Integer code;
    private String decs;

    public static ResultVo ok() {
        ResultVo vo = new ResultVo();
        vo.setCode(200);
        vo.setDecs("OK");
        return vo;
    }
}
