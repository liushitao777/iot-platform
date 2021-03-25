package com.cpiinfo.iot.commons.exception;

/**
 * 错误编码
 * @author liwenjie
 * @date 2020-05-11
 */
public interface ErrorCode {
    /**
     * 错误请求
     * 此代码表示服务器无法理解请求，因为语法无效。这是因为发送到服务器的请求具有无效语法。
     */
    int BAD_REQUEST = 400;
    /**
     * 服务器遇到错误
     */
    int INTERNAL_SERVER_ERROR = 500;
    /**
     * 未经授权的错误
     */
    int UNAUTHORIZED = 401;
    /**
     * 禁止访问
     */
    int FORBIDDEN = 403;
    /**
     * 无法找到资源
     */
    int No_RESOURCES = 404;
    /**
     * 数据已存在
     */
    int DB_RECORD_EXISTS = 10000;
    /**
     * 值为空
     */
    int NOT_NULL = 10001;

}
