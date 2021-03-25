package com.cpiinfo.sysmgt.entity;

/**   
* @Title: GensetBean.java 
* @Package com.cpiinfo.iisp.manager.entity
* @Description: TODO(用一句话描述该文件做什么) 
* @author 杨永前
* @date 2016年10月22日 下午4:52:20 
* @version V1.0   
*/
public class GensetBean {
	private String id; //主键
	private String gensetName;//机组名称
	private String gensetCode;//机组名称
	private String gensetUrl;//机组组态图页面URL地址
    private String unit;  //所属单位
    private String unitCode;  //单位编码
    
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
	 * @return the gensetName
	 */
	public String getGensetName() {
		return gensetName;
	}
	/**
	 * @param gensetName the gensetName to set
	 */
	public void setGensetName(String gensetName) {
		this.gensetName = gensetName;
	}
	/**
	 * @return the gensetUrl
	 */
	public String getGensetUrl() {
		return gensetUrl;
	}
	/**
	 * @param gensetUrl the gensetUrl to set
	 */
	public void setGensetUrl(String gensetUrl) {
		this.gensetUrl = gensetUrl;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}
	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getGensetCode() {
		return gensetCode;
	}
	public void setGensetCode(String gensetCode) {
		this.gensetCode = gensetCode;
	}
}
