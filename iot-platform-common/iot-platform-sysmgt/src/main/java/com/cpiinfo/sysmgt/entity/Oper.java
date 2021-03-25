package com.cpiinfo.sysmgt.entity;

public class Oper {
	
	private String id;  //操作ID
	
	private String code; //操作编码

    private String name;  //操作名
    
    private boolean enabled; //是否有权限

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
