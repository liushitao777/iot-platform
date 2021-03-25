package com.cpiinfo.sysmgt.entity;

public class MenuOper {
	private String id;//资源和操作关系表ID	
	
	private String resourceId; //资源ID
	
	private String operId; //操作ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

}
