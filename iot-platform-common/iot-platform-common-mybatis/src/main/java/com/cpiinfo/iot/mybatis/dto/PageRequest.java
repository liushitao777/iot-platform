package com.cpiinfo.iot.mybatis.dto;

import org.apache.ibatis.session.RowBounds;

/**
 * 
 * @author yabo
 * @date 2019/09/16
 */
public class PageRequest extends RowBounds{
	
	private int offset = 0;
	
	private int pageNum = 0;
	
	private int pageSize = Integer.MAX_VALUE;

	public PageRequest() {
		super();
	}

	public static PageRequest fromOffset(int offset, int fetchRows) {
		PageRequest page = new PageRequest();
		page.offset = offset;
		page.pageSize = fetchRows;
		return page;
	}
	
	public PageRequest(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.offset = (this.pageNum - 1) * this.pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int getOffset() {
		return this.offset;
	}

	@Override
	public int getLimit() {
		return this.pageSize;
	}
	
	
}
