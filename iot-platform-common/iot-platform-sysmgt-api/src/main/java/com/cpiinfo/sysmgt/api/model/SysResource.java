package com.cpiinfo.sysmgt.api.model;

import java.util.List;

public class SysResource {

	private String resId;
	
	private String resName;

	private String resNameCode;
	
	private String resPath;
	
	private String resType;
	
	private Integer sort;
	
	private String resUrl;
	
	private String parentId;
	
	private String icon;
	
	private String loadFile;

	private String pageCode;
	
	private String state;

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public String getResNameCode() {
		return resNameCode;
	}

	public void setResNameCode(String resNameCode) {
		this.resNameCode = resNameCode;
	}

	private List<SysRole> grantRoles;

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResPath() {
		return resPath;
	}

	public void setResPath(String resPath) {
		this.resPath = resPath;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLoadFile() {
		return loadFile;
	}

	public void setLoadFile(String loadFile) {
		this.loadFile = loadFile;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<SysRole> getGrantRoles() {
		return grantRoles;
	}

	public void setGrantRoles(List<SysRole> grantRoles) {
		this.grantRoles = grantRoles;
	}
}
