package com.cpiinfo.iot.websocket.server.service.impl;

import javax.websocket.Session;

/**
 * @ClassName WebSocketSession
 * @Deacription 公共WebSocket会话对象
 * @Author yangbo
 * @Date 2020年09月07日 20:08
 * @Version 1.0
 **/
public class WebSocketSession {

	/**
	 * WebSocket 会话对象
	 */
	private Session session;
	
	/**
	 * 连接WebSocket的用户编码
	 */
	private String userCode;
	
	/**
	 * WebSocket客户端组别  browser, android, bigscreen
	 */
	private String clientGroup;
	
	/**
	 * 推送过滤－数据类型
	 *  uav - 无人机
	 *  robot - 机器人
	 *  security/fire - 火情
	 *  security/cable - 电缆头
	 *  security/door - 门禁
	 *  security/brake - 车闸
	 *  security/fence - 围栏
	 *  security/person - 人员
	 */
    
	private String dataTypeFilter;
	
	/**
	 * 推送过滤－数据值范围，依据过滤数据类型取不同值
	 *  dataTypeFilter = uav时，为无人机编码
	 *  dataTypeFilter = robot时，为机器人编码
	 *  dataTypeFilter = security/cable时，为电缆头设备编码
	 *  
	 */
	private String dataScopeFilter;

	/** empty-推送空数据  */
	private String emptyMessage;

	/**
	 * 是否已设置过滤条件，只有已设置了过滤条件的能会推送消息
	 */
	private boolean filterApplied = false;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getClientGroup() {
		return clientGroup;
	}

	public void setClientGroup(String clientGroup) {
		this.clientGroup = clientGroup;
	}

	public String getDataTypeFilter() {
		return dataTypeFilter;
	}

	public void setDataTypeFilter(String dataTypeFilter) {
		this.dataTypeFilter = dataTypeFilter;
	}

	public String getDataScopeFilter() {
		return dataScopeFilter;
	}

	public void setDataScopeFilter(String dataScopeFilter) {
		this.dataScopeFilter = dataScopeFilter;
	}

	public String getEmptyMessage() {
		return emptyMessage;
	}

	public void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	public boolean isFilterApplied() {
        return filterApplied;
    }

    public void setFilterApplied(boolean filterApplied) {
        this.filterApplied = filterApplied;
    }
}
