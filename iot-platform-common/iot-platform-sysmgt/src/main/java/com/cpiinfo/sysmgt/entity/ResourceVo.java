package com.cpiinfo.sysmgt.entity;

import java.util.ArrayList;
import java.util.List;

public class ResourceVo  {
	private String cid;
	private String cname;
	private String pid;
	private String url;
	private List<ResourceVo> nodes = new ArrayList<ResourceVo>();
	 
	public ResourceVo() {
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<ResourceVo> getNodes() {
		return nodes;
	}

	public void setNodes(List<ResourceVo> nodes) {
		this.nodes = nodes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
