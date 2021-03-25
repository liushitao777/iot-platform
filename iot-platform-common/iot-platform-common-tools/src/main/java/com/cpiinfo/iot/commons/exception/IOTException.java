package com.cpiinfo.iot.commons.exception;

import lombok.Data;
/**
 * 自定义异常
 *
 * @author liwenjie
 * @date 2020-05-11
 */
@Data
public class IOTException extends RuntimeException {

	private static final long serialVersionUID = 1L;
    private int code;
	private String msg;

	public IOTException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public IOTException(String msg) {
		super(msg);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}

	public IOTException(String msg, Throwable e) {
		super(msg, e);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}


}