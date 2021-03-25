package com.cpiinfo.sysmgt.entity;

import java.util.List;

public class Menu {
    private String id;

    private String rname;  //资源名

    private String rnameCode;

    private String sort;  //序号

    private String parentId;  //上一级资源id

    private String url;  //页面地址

    private String loadfile;  //加载的js

    private String icon;  // 图标

    private String updateUser;  //修改人

    private String updateTime;  //修改时间
 
    private String state;  //状态  0不可见   1可见
    
    private List<MenuOper> menuOpers; //菜单关联操作关系
    
    private List<Oper> opers; //菜单关联字典
    
    private String resPath;//资源路径

    private String pageCode;//页面编码 前端:front 后台:back

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    private List<Menu> children;//子菜单

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getRnameCode() {
        return rnameCode;
    }

    public void setRnameCode(String rnameCode) {
        this.rnameCode = rnameCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoadfile() {
        return loadfile;
    }

    public void setLoadfile(String loadfile) {
        this.loadfile = loadfile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public List<MenuOper> getMenuOpers() {
		return menuOpers;
	}

	public void setMenuOpers(List<MenuOper> menuOpers) {
		this.menuOpers = menuOpers;
	}

	public List<Oper> getOpers() {
		return opers;
	}

	public void setOpers(List<Oper> opers) {
		this.opers = opers;
	}

	public String getResPath() {
		return resPath;
	}

	public void setResPath(String resPath) {
		this.resPath = resPath;
	}

	
	
}