package com.cpiinfo.iot.commons.exception;

import lombok.Data;

/**
 * @program: iot-platform
 * @description: 对接生产
 * @author: ShenZe
 * @create: 2020-11-12
 **/
@Data
public class ExternalException extends RuntimeException{
    private int result;
    private String code;
    private String msgDesc;

    public ExternalException(String taskCode, String msg) {
        super(msg);
        this.code = taskCode;
        this.msgDesc = msg;
    }
}
