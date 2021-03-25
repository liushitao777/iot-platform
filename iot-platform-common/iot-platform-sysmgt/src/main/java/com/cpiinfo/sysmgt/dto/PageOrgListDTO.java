package com.cpiinfo.sysmgt.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wuchangjiang
 * @version V1.0
 * @Title:
 * @Package com.cpiinfo.sysmgt.dto
 * @Description:
 * @date 2020年04月23日 14:35
 */
@ApiModel("组织信息")
public class PageOrgListDTO {
    @ApiModelProperty(value = "单位编码")
    private String orgCode;
    @ApiModelProperty(value = "单位名称")
    private String orgName;
    @ApiModelProperty(value = "当前页")
    private int currentPage;
    @ApiModelProperty(value = "每页条数")
    private int pageSize;

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
