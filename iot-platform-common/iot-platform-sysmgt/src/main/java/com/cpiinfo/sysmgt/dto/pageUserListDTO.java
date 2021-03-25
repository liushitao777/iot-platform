package com.cpiinfo.sysmgt.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wuchangjiang
 * @version V2.0
 * @Title:
 * @Package com.cpiinfo.sysmgt.dto
 * @Description:
 * @date 2020年05月09日 12:39
 */
public class pageUserListDTO {
    @ApiModelProperty(value = "单位编码")
    private String orgCode;
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "当前页")
    private int page;
    @ApiModelProperty(value = "每页条数")
    private int limit;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
