package com.xzp.smartcampus.common.advice;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author bystander
 * @date 2018/9/15
 * <p>
 * 自定义异常处理
 */
@Slf4j
@ControllerAdvice
public class BasicExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResult> handleException(Exception e) {
        log.error("service error ", e);
        if (e instanceof SipException) {
            SipException sipException = (SipException) e;
            return ResponseEntity.status(sipException.getCode())
                    .body(new ExceptionResult(sipException));
        }
        return ResponseEntity.status(500)
                .body(new ExceptionResult(500, e.getMessage()));
    }
}
