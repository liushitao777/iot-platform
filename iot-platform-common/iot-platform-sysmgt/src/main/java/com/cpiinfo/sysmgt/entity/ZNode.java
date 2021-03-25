package com.cpiinfo.sysmgt.entity;

public class ZNode {
    private String ids;
	private String id;
	private String pId;  //父id
	private String name;  //名称
	private boolean open;  //是否展开
	private boolean checked;  //是否选中
	private boolean chkDisabled; //是否禁用
	private String url;//点击节点的URL
	private String qx="0";
	private String leveln;
	private String cy;//产业板块
	private String orgOrDepart;//产业板块
	private String isDept;//是否是部门
	/**
	 * @return the orgOrDepart
	 */
	public String getOrgOrDepart() {
		return orgOrDepart;
	}
	/**
	 * @param orgOrDepart the orgOrDepart to set
	 */
	public void setOrgOrDepart(String orgOrDepart) {
		this.orgOrDepart = orgOrDepart;
	}
	public String getCy() {
		return cy;
	}
	public void setCy(String cy) {
		this.cy = cy;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getId() {
		return id;
	}
	
	public String getIsDept() {
		return isDept;
	}
	public void setIsDept(String isDept) {
		this.isDept = isDept;
	}
	@Override
	public String toString() {
		return "ZNode [id=" + id + ", pId=" + pId + ", name=" + name + ", open=" + open + "]";
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChkDisabled() {
		return chkDisabled;
	}
	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	public String getQx() {
		return qx;
	}
	public void setQx(String qx) {
		this.qx = qx;
	}
	public String getLeveln() {
		return leveln;
	}
	public void setLeveln(String leveln) {
		this.leveln = leveln;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}
