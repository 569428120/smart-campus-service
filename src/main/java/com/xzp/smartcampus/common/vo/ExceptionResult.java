package com.xzp.smartcampus.common.vo;

import com.xzp.smartcampus.common.exception.SipException;
import lombok.Data;

/**
 * @author bystander
 * @date 2018/9/15
 * <p>
 * 自定义异常结果类
 */

@Data
public class ExceptionResult {

    private int status;

    private String message;

    private long timestamp;

    public ExceptionResult(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(SipException e) {
        this.status = e.getCode();
        this.message = e.getMessage();
        this.timestamp = System.currentTimeMillis();
    }
}
