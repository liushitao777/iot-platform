package com.cpiinfo.sysmgt.entity;

public class Dictionary {

	private String id;
	private String name; //能源名
	private String type;  //类型  1：添加子单位时的主要能源
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
