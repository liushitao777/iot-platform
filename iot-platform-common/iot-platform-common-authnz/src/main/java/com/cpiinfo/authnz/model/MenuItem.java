package com.cpiinfo.authnz.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "菜单信息", description = "功能菜单数据对象，用户登录后从后端读取在界面上生成操作菜单")
public class MenuItem {

    @ApiModelProperty(value = "菜单标识", example = "1")
    private String id;
    
    @ApiModelProperty(value = "上级菜单标识", example = "风电日填报")
    private String pid;

    @ApiModelProperty(value = "菜单名称", example = "环保指标月填报")
    private String name;

	@ApiModelProperty(value = "菜单名称code", example = "modelUse")
    private String nameCode;

    @ApiModelProperty(value = "菜单图标", example = "iconfont icon-huanbao")
    private String icon;

    @ApiModelProperty(value = "菜单跳转地址", example = "/iisp-spic/views/dayDataInput.html")
    private String url;
    
    @ApiModelProperty(value = "类型 0-菜单 1-操作/按钮")
    private String type;
    
    @ApiModelProperty(value = "排序，小值在前")
    private Integer sort;
    
    @ApiModelProperty(value = "权限描述")
    private String permissions;

	@ApiModelProperty(value = "页面编码 前端:front 后台:back")
	private String pageCode;

	@ApiModelProperty(value = "下级菜单列表", example = "")
	private List<MenuItem> children;

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}
}
