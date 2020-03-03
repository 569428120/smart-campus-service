package com.xzp.smartcampus.common.exception;

import lombok.Getter;

/**
 * @author bystander
 * @date 2018/9/15
 * <p>
 * 自定义异常类
 */
@Getter
public class SipException extends RuntimeException {

    private Integer code = 500;

    public SipException(String message) {
        super(message);
    }


    public SipException(String message, Integer code) {
        super(message);
        this.code = code;
    }


}
