package com.cpiinfo.sysmgt.api.model;

import java.io.Serializable;

public class SysRole implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String STATE_NORMAL = "1";
	
	public static final String STATE_DELETED = "0";
	
	private String roleId;
	
	private String roleCode;
	
	private String roleName;
	
	private String state;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
