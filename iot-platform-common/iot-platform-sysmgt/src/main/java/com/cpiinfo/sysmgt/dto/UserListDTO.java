package com.cpiinfo.sysmgt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author
 * @version V2.0
 * @Title:
 * @Package com.cpiinfo.sysmgt.dto
 * @Description:
 * @date 2020年05月09日 16:35
 */
@ApiModel("员工列表信息")
public class UserListDTO {
    @ApiModelProperty("员工编号")
    private String id;
    @ApiModelProperty("用户账号")
    private String userCode;
    @ApiModelProperty("用户姓名")
    private String username;
    @ApiModelProperty("密码")
    private String userpwd;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("角色id")
    private String userType;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("所属单位")
    private String unit;
    @ApiModelProperty("单位编码")
    private String unitCode;
    @ApiModelProperty("单位名称")
    private String unitName;
    @ApiModelProperty("单位id")
    private String unitId;
    @ApiModelProperty("职务/岗位")
    private String duties;
    @ApiModelProperty("状态      0禁用    1正常   2删除状态")
    private String state;
    @ApiModelProperty("备注")
    private String postscript;
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @ApiModelProperty(" 最后一次修改密码时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastChangePassword;
    @ApiModelProperty("用户锁定截止时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockEndTime;
    @ApiModelProperty("更新时间")
    private Boolean isFirstLogin;
    @ApiModelProperty("厂商密码")
    private String makerPassword;
    @ApiModelProperty("厂商用户id")
    private String makeruserId;
    @ApiModelProperty("部门名称")
    private String departName;
    private String departId;

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
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

    public Boolean getFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public String getMakerPassword() {
        return makerPassword;
    }

    public void setMakerPassword(String makerPassword) {
        this.makerPassword = makerPassword;
    }

    public String getMakeruserId() {
        return makeruserId;
    }

    public void setMakeruserId(String makeruserId) {
        this.makeruserId = makeruserId;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
