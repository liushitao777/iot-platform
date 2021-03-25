package com.cpiinfo.iot.polymerize.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @ClassName TenantProperties
 * @Deacription 项目多租户支持
 * @Author yangbo
 * @Date 2020年09月02日 20:08
 * @Version 1.0
 **/
@Data
@Component("platform-tenantProperties")
@ConfigurationProperties(prefix = TenantProperties.PREFIX)
public class TenantProperties {
    public static final String PREFIX = "com.cpiinfo.iot.tenant";

    /**
     * 默认租户，开发环境使用
     */
    private String defaultTenant;
    
    /**
     * 租户的数据源映射
     */
    private Map<String, String> datasourceMapping;
}
