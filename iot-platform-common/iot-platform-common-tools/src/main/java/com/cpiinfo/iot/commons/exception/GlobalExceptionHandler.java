package com.cpiinfo.iot.commons.exception;

import com.alibaba.fastjson.JSONObject;
import com.cpiinfo.iot.commons.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandler
 * @Description: TODO
 * @Author LuoWei
 * @Date 2020/7/29
 * @Version V1.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result violationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(message, exception);
        return new Result().error(message);
    }

    @ExceptionHandler(BindException.class)
    public Result bindException(BindException exception) {
        String message = exception.getAllErrors().get(0).getDefaultMessage();
        log.error(message, exception);
        return new Result().error(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result bindException(RuntimeException exception) {
        String message = exception.getMessage();
        log.error(message, exception);
        return new Result().error(message);
    }

    @ExceptionHandler(ExternalException.class)
    public JSONObject handleExternal(ExternalException e) {
        JSONObject r = new JSONObject(3);
        r.put("code", e.getCode());
        r.put("result", e.getResult());
        r.put("msgDesc", "服务器内部错误 错误原因: " + e.getMessage());
        log.error(e.getMsgDesc(), e);
        return r;
    }

    /**
     //     * 处理自定义异常
     //     */
    @ExceptionHandler(IOTException.class)
    public Result handleRRException(IOTException ex) {
        Result result = new Result();
        result.error(ex.getCode(), ex.getMsg());
        log.error(ex.getMsg(), ex);
        return result;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException ex) {
        log.error(ex.getMessage(), ex);
        return new Result().error(ErrorCode.DB_RECORD_EXISTS, "数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new Result().error("服务器内部错误， 错误原因: " + ex.getMessage());
    }

}
