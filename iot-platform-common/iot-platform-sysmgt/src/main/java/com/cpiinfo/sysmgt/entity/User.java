package com.cpiinfo.sysmgt.entity;

import java.util.Date;


public class User {
    private String id; 

    private String userCode;  //用户编号

    private String username;  //用户姓名

    private String userpwd;  //密码


    private String md5Pwd;  //md5密文

    private String sex;  //性别

    private String phone;  //手机

    private String email;  //邮箱

    private String userType;  //员工类型 角色id

    private String unit;  //所属单位
    
    private String unitCode;  //单位编码
    
    private String unitId;  //单位id

    private String duties;  //职务

    private String state;  //状态      0禁用    1正常   2删除状态
    
    private String postscript;  //备注

    private String loginType;

    private Integer failsCount;

    private Boolean active;

    private Date updateTime;  //更新时间

    private Date lastChangePassword;  //最后一次修改密码时间

    private Date lockEndTime;  //用户锁定截止时间

    private Boolean isFirstLogin;

    private String makerPassword;

    private String makeruserId;//厂商用户id

    private Long totalLoginTime;

    private Date    lastLoginTime;

    private String    departId;

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

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

    public Boolean getFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(Boolean isfirstLogin) {
        isFirstLogin = isfirstLogin;
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



    public String getMd5Pwd() {
        return md5Pwd;
    }

    public void setMd5Pwd(String md5Pwd) {
        this.md5Pwd = md5Pwd;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}