package com.cpiinfo.iot.model;

/**   
 * @Title: ServiceResponse.java 
 * @Package com.cpiinfo.iot.model
 * @Description: 请求返回封装对象
 * @author 徐海滨
 * @date 2016年8月31日 下午9:45:36 
 * @version V1.0   
 */
public class ServiceResponse {

	boolean success;	//是否成功
	int resultCode;		//结果代码
	String message;		//消息
	int total;			//总数
	Object data;		//数据

	public ServiceResponse(boolean success, String message, int total, Object data) {
		this(success, 0, message, total, data);
	}
	
	public ServiceResponse(boolean success, int resultCode, String message, int total, Object data) {
		this.success = success;
		this.resultCode = resultCode;
		this.message = message;
		this.total = total;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
