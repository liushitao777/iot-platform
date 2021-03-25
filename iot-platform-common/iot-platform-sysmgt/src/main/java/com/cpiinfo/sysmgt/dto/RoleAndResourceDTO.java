package com.cpiinfo.sysmgt.dto;

import java.util.Date;
import java.util.List;

/**
 * @author wuchangjiang
 * @version V2.0
 * @Title:
 * @Package com.cpiinfo.sysmgt.dto
 * @Description:
 * @date 2020年05月09日 16:44
 */
public class RoleAndResourceDTO {
    private String roleId; //主键id

    private String roleCode;

    private String roleName;  //角色名

    private String roleDesc;  //角色描述

    private String updateUser;  //修改人账号

    private Date updateTime;  //修改时间

    private String state;  // 0删除   1正常

    private String root;  //是否管理员   0非管理员    1管理员

    private String level;  //级别

    private String unit;  //所属单位编码

    private String department;
    private String pageCode;

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    private List<String> resourcesLsit;//权限菜单集合

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getResourcesLsit() {
        return resourcesLsit;
    }

    public void setResourcesLsit(List<String> resourcesLsit) {
        this.resourcesLsit = resourcesLsit;
    }
}
