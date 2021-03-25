package com.cpiinfo.iot.polymerize.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import  com.cpiinfo.authnz.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cpiinfo.iot.datasource.toolkit.DynamicDataSourceContextHolder;
import com.cpiinfo.iot.polymerize.config.TenantProperties;

import io.micrometer.core.lang.Nullable;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName SwitchDataSourceInterceptor
 * @Deacription TODO 统一切库拦截器
 * @Author liwenjie
 * @Date 2020/7/19 8:47
 * @Version 1.0
 **/
@Slf4j
@Component("platform-switchDataSourceInterceptor")
public class SwitchDataSourceInterceptor extends GenericFilterBean implements HandlerInterceptor {
    
    /**
     * 默认租户ID，用于在开发环境取得数据源，生产环境租户ID必须在登录时传入
     */
    @Value("${iot.tenant.default-tenant:}")
    private String DEFAULT_TENANT_ID;
    
    @Value("${com.cpiinfo.iot.authnz.login-processing-url:/login}")
    private String loginProcessingUrl;
    
    @Autowired
    private TenantProperties tenantProperties;
    
    /**
     * 请求处理前，通过登录用户的组织机构编码取得相应数据源
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String dsName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        if(auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            dsName = getDatasourceForTenant(userDetails.getTenant());
        }
        if(dsName == null || "".equals(dsName)) {
            request.setAttribute("___SkipDynamicDataSource__", Boolean.TRUE);
            return true;
        }
        else {
            DynamicDataSourceContextHolder.push(dsName);
            return true;
        }
        

    }
    
    /**
     * 取得指定租户的数据源名称
     * @param tenant - 租户
     * @return 返回数据源名称
     */
    private String getDatasourceForTenant(String tenant) {
        String dsName = null;
        if(tenant != null) {
            Map<String, String> mapping = tenantProperties.getDatasourceMapping();
            dsName = mapping.get(tenant);
            if(dsName == null || "".equals(tenant)) {
                dsName = tenant;
            }
        }
        return dsName;
    }
    /**
     * afterCompletion 视图渲染完成之后执行, 需要preHandle返回true，
     * 清除数据源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Object obj = request.getAttribute("___SkipDynamicDataSource__");
        if(!Boolean.TRUE.equals(obj)) {
            DynamicDataSourceContextHolder.pop();
        }
    }

    /**
     * 登录时使用登录参数中的公司Id作为数据源
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String dsName = null;
        if(request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            String url = httpRequest.getRequestURI();
            if(url != null && url.equals(loginProcessingUrl)) {
                String userName = obtainParameter(httpRequest, "username");
                if(userName != null && !"".equals(userName)) {
                    String tenant = obtainParameter(httpRequest, "tenant");
                    if(tenant == null || "".equals(tenant)) {
                        if(DEFAULT_TENANT_ID == null || "".equals(DEFAULT_TENANT_ID)) {
                            throw new RuntimeException("未指定登录租户，开发环境请配置默认租户参数");
                        }
                        if(log.isWarnEnabled()) {
                            log.warn("warn: there is not a tenant specified for login.", new Throwable("未指定登录租户，取默认数据源"));
                        }
                        tenant = DEFAULT_TENANT_ID;
                        request = new HttpServletRequestWrapper(httpRequest) {
                            @Override
                            public String getParameter(String name) {
                                if("tenant".equals(name)) {
                                    return DEFAULT_TENANT_ID;
                                }
                                return super.getParameter(name);
                            }
                        };
                    }
                    dsName = getDatasourceForTenant(tenant);
                }
            }
        }
        if(dsName == null || "".equals(dsName)) {
            chain.doFilter(request, response);
        }
        else {
            DynamicDataSourceContextHolder.push(dsName);
            try {
                chain.doFilter(request, response);
            }
            finally {
                DynamicDataSourceContextHolder.pop();
            }
        }
    }
    
    private String obtainParameter(HttpServletRequest request, String parameter) {
        String paramValue = request.getParameter(parameter);
        if(paramValue == null || "".equals(paramValue)) {
            JsonNode json = (JsonNode)request.getAttribute("___RequestBodyInfo__" + request.toString());
            if(json == null) {
                ObjectMapper jsonMapper = new ObjectMapper();
                try {
                    json = jsonMapper.readTree(request.getInputStream());
                    request.setAttribute("___RequestBodyInfo__" + request.toString(), json);
                } catch (IOException e) {
                    if(logger.isDebugEnabled()) {
                        logger.debug("read user login info error. ", e);
                    }
                    else {
                        logger.warn("read user login info error. " + e.getMessage());
                    }
                }
            }
            paramValue = json == null || json.get(parameter) == null ? null : json.get(parameter).textValue();
        }
        return paramValue;
    }
    
    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }
}

