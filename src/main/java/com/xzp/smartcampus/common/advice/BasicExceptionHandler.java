package com.xzp.smartcampus.common.advice;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

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
        if (e instanceof SipException) {
            log.warn(e.getMessage());
            SipException sipException = (SipException) e;
            return ResponseEntity.status(sipException.getCode())
                    .body(new ExceptionResult(sipException));
        }
        log.error("service error ", e);
        return ResponseEntity.status(500)
                .body(new ExceptionResult(500, e.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResult> handleValidException(BindException e) {
        return ResponseEntity.status(500).body(new ExceptionResult(500, this.getValidMessage(e.getBindingResult())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResult> handleValidException1(MethodArgumentNotValidException e) {
        return ResponseEntity.status(500).body(new ExceptionResult(500, this.getValidMessage(e.getBindingResult())));
    }


    /**
     * 获取校验错误提示
     *
     * @param result result
     * @return String
     */
    private String getValidMessage(BindingResult result) {
        if (result == null) {
            return "";
        }
        List<FieldError> fieldErrors = result.getFieldErrors();
        if (CollectionUtils.isEmpty(fieldErrors)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        fieldErrors.forEach(item -> {
            sb.append(item.getDefaultMessage()).append("\r\n");
        });
        return sb.toString();
    }

}
