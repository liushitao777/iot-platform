/**
 * 
 */
package com.cpiinfo.sysmgt.entity;

/**
 * 
 * @Title: DepartUser.java
 * @Package com.cpiinfo.jyspsc.sys.manager.entity
 * @Description: TODO()
 * @author 程志宏
 * @date 2017年7月18日下午2:32:04
 * @version V1.0
 */
public class DepartUser {
	
	private String id;
	private String departId;
	private String userId;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the departId
	 */
	public String getDepartId() {
		return departId;
	}
	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
