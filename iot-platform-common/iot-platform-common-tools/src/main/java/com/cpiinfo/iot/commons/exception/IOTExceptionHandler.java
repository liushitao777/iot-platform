//package com.cpiinfo.iot.commons.exception;
//
//import com.alibaba.fastjson.JSONObject;
//import com.cpiinfo.iot.commons.utils.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
///**
// * 异常处理器
// *
// * @author liwenjie
// * @date 2020-05-11
// */
//@RestControllerAdvice
//@Slf4j
//public class IOTExceptionHandler {
//    /**
//     * 处理自定义异常
//     */
//    @ExceptionHandler(IOTException.class)
//    public Result handleRRException(IOTException ex) {
//        Result result = new Result();
//        result.error(ex.getCode(), ex.getMsg());
//        log.error(ex.getMsg(), ex);
//        return result;
//    }
//
//    @ExceptionHandler(DuplicateKeyException.class)
//    public Result handleDuplicateKeyException(DuplicateKeyException ex) {
//        log.error(ex.getMessage(), ex);
//        return new Result().error(ErrorCode.DB_RECORD_EXISTS, "数据库中已存在该记录");
//    }
//
//    @ExceptionHandler(Exception.class)
//    public Result handleException(Exception ex) {
//        log.error(ex.getMessage(), ex);
//        return new Result().error("服务器内部错误");
//    }
//
//    @ExceptionHandler(ExternalException.class)
//    public JSONObject handleExternal(ExternalException e) {
//        JSONObject r = new JSONObject(3);
//        r.put("code", e.getCode());
//        r.put("result", e.getResult());
//        r.put("msgDesc", "服务器内部错误");
//        log.error(e.getMsgDesc(), e);
//        return r;
//    }
//
//}