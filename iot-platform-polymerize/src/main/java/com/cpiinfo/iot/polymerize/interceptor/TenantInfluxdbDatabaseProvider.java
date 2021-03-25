package com.cpiinfo.iot.polymerize.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.cpiinfo.iot.commons.influxdb.InfluxdbDatabaseProvider;
import com.cpiinfo.iot.datasource.toolkit.DynamicDataSourceContextHolder;
import com.cpiinfo.iot.polymerize.config.TenantProperties;

/**
 * @ClassName TenantInfluxdbDatabaseProvider
 * @Deacription 多租户Influxdb支持
 * @Author yangbo
 * @Date 2020年09月02日 20:08
 * @Version 1.0
 **/
@ConditionalOnProperty(name = "com.cpiinfo.iot.influxdb.url", matchIfMissing = false)
@Component("platform-tenantInfluxdbDatabaseProvider")
public class TenantInfluxdbDatabaseProvider implements InfluxdbDatabaseProvider {

    @Autowired
    private TenantProperties tenantProperties;
    
    @Override
    public String database() {
        String database = DynamicDataSourceContextHolder.peek();
        if(database == null || "".equals(database)) {
            database = tenantProperties.getDatasourceMapping().get(tenantProperties.getDefaultTenant());
        }
        if(database == null || "".equals(database)) {
            throw new RuntimeException("there is not a influxdb for current tenant. ");
        }
        return database;
    }

}
