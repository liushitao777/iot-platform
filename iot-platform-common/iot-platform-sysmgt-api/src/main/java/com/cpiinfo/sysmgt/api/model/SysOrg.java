package com.cpiinfo.sysmgt.api.model;

import java.io.Serializable;

/**
 * 系统组织模型对象
 * <p>
 * 系统组织模型对象由系统管理模块管理，作为API接口模型提供其它模块与系统管理模块交互
 *
 * @author yabo
 * @version 1.0
 * @date  2019/11/11
 * @apiNote 初始版本
 */
public class SysOrg implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 组织编码
	 */
    private String orgCode;
    
    /**
     * 组织名称
     */
    private String orgName;
    
    /**
     * 人员所在组织的组织类型 
     * L2ORG-二级单位 
     * ENAREA-区域  
     * POWER_PLANT-电厂
     */
    private String orgType;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
}