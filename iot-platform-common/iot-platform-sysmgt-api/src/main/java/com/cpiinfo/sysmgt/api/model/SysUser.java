package com.cpiinfo.sysmgt.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户模型对象
 * <p>
 * 系统用户模型对象由系统管理模块管理，作为API接口模型提供其它模块与系统管理模块交互
 *
 * @author yabo
 * @version 1.0
 * @date  2019/11/11
 * @apiNote 初始版本
 */
public class SysUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String STATUS_NORMAL = "1";
	
	public static final String STATUS_DISABLED = "0";
	
	public static final String STATUS_DELETED = "2";
	
	private String userId;
	
	private String userCode;
	
	private String userName;
	
	private String userPwd;
	
	private String phone;
	
	private String email;
	
	private String state;
	
	private String orgCode;
	
	private String orgName;
	
	private String orgType;

	private Integer failsCount;

	private Boolean active;

	private Date lastChangePassword;  //最后一次修改密码时间

	private Date lockEndTime;  //用户锁定截止时间

	private Boolean isFirstLogin;

	private Boolean isExpired =false;

	private String makerPassword;//厂商用户密码

	private String makeruserId;//厂商用户id

	private Long totalLoginTime;

	private Date    lastLoginTime;

	public Long getTotalLoginTime() {
		return totalLoginTime;
	}

	public void setTotalLoginTime(Long totalLoginTime) {
		this.totalLoginTime = totalLoginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getMakeruserId() {
		return makeruserId;
	}

	public void setMakeruserId(String makeruserId) {
		this.makeruserId = makeruserId;
	}

	public String getMakerPassword() {
		return makerPassword;
	}

	public void setMakerPassword(String makerPassword) {
		this.makerPassword = makerPassword;
	}

	public Boolean getExpired() {
		return isExpired;
	}

	public void setExpired(Boolean expired) {
		isExpired = expired;
	}

	public Boolean getFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(Boolean firstLogin) {
		isFirstLogin = firstLogin;
	}

	public Date getLastChangePassword() {
		return lastChangePassword;
	}

	public void setLastChangePassword(Date lastChangePassword) {
		this.lastChangePassword = lastChangePassword;
	}

	public Date getLockEndTime() {
		return lockEndTime;
	}

	public void setLockEndTime(Date lockEndTime) {
		this.lockEndTime = lockEndTime;
	}

	public Integer getFailsCount() {
		return failsCount;
	}

	public void setFailsCount(Integer failsCount) {
		this.failsCount = failsCount;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}




	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	private String loginType;

	private List<SysOrg> userOrgs;
	
	private List<SysRole> userRoles;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

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

	public List<SysOrg> getUserOrgs() {
		return userOrgs;
	}

	public void setUserOrgs(List<SysOrg> userOrgs) {
		this.userOrgs = userOrgs;
	}

	public List<SysRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<SysRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "SysUser{" +
				"userId='" + userId + '\'' +
				", userCode='" + userCode + '\'' +
				", userName='" + userName + '\'' +
				", userPwd='" + userPwd + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", state='" + state + '\'' +
				", orgCode='" + orgCode + '\'' +
				", orgName='" + orgName + '\'' +
				", orgType='" + orgType + '\'' +
				", failsCount=" + failsCount +
				", active=" + active +
				", lastChangePassword=" + lastChangePassword +
				", lockEndTime=" + lockEndTime +
				", isFirstLogin=" + isFirstLogin +
				", isExpired=" + isExpired +
				", makerPassword='" + makerPassword + '\'' +
				", makeruserId='" + makeruserId + '\'' +
				", totalLoginTime=" + totalLoginTime +
				", lastLoginTime=" + lastLoginTime +
				", loginType='" + loginType + '\'' +
				", userOrgs=" + userOrgs +
				", userRoles=" + userRoles +
				'}';
	}
}
