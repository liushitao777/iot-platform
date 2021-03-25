package com.cpiinfo.iot.mybatis.batch;

import java.util.List;

public interface BatchOperation {

	/**
	 * 数据对象批量插入
	 * @param daoClass - 数据访问对象类别
	 * @param method - 单条数据对象插入方法
	 * @param entities - 要批量插入的数据对象列表
	 * @param batchSize - 每插入多少对象即进行一次提交的数量
	 * @return 
	 */
	public List<Integer> batchInsert(Class<?> daoClass, String method, List<? extends Object> entities, int batchSize);
	
	/**
	 * 数据对象批量更新
	 * @param daoClass - 数据访问对象类别
	 * @param method - 单条数据对象更新方法
	 * @param entities - 要批量更新的数据对象列表
	 * @param batchSize - 每更新多少对象即进行一次提交的数量
	 * @return 
	 */
	public List<Integer> batchUpdate(Class<?> daoClass, String method, List<? extends Object> entities, int batchSize);
}
