/*
 * 文件名：AuthnzProperties.java
 * 版权：〈版权〉
 * 描述：〈描述〉
 * 修改人：yabo
 * 修改时间：2020年10月28日
 * 修改内容：新建
 */

package com.cpiinfo.authnz.configurer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 认证功能模块属性设置
 * 设置认证功能模块相关系统属性属性
 * @author    yabo
 * @version   [1.0.8, 2020年10月28日]
 */
@ConfigurationProperties(prefix = AuthnzProperties.PREFIX)
@Component
public class AuthnzProperties {
	public static final String PREFIX = "com.cpiinfo.iot.authnz";

	/**
	 * 登录处理的地址
	 */
	private String loginProcessingUrl = "/login";
	
	/**
	 * 登录页面地址
	 */
	private String loginPage = "";
	
	/**
	 * 首页地址
	 */
	private String indexPage = "/";
	
	/**
	 * 忽略的地址列表
	 */
	private List<String> ignoreUrls;

	public String getLoginProcessingUrl() {
		return loginProcessingUrl;
	}

	public void setLoginProcessingUrl(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public String getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

	public List<String> getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(List<String> ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}
}
