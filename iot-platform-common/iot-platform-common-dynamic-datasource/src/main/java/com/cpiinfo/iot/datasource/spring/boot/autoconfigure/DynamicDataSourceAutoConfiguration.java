/**
 * Copyright © 2018 organization baomidou
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <pre/>
 */
package com.cpiinfo.iot.datasource.spring.boot.autoconfigure;

import javax.sql.DataSource;

import com.cpiinfo.iot.datasource.DynamicDataSourceConfigure;
import com.cpiinfo.iot.datasource.DynamicDataSourceCreator;
import com.cpiinfo.iot.datasource.DynamicRoutingDataSource;
import com.cpiinfo.iot.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.cpiinfo.iot.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.cpiinfo.iot.datasource.plugin.DbHealthIndicator;
import com.cpiinfo.iot.datasource.provider.DynamicDataSourceProvider;
import com.cpiinfo.iot.datasource.provider.YmlDynamicDataSourceProvider;
import com.cpiinfo.iot.datasource.strategy.DynamicDataSourceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import com.cpiinfo.iot.datasource.aop.DynamicDataSourceAdvisor;
import com.cpiinfo.iot.datasource.processor.DsHeaderProcessor;
import com.cpiinfo.iot.datasource.processor.DsProcessor;
import com.cpiinfo.iot.datasource.processor.DsSessionProcessor;
import com.cpiinfo.iot.datasource.processor.DsSpelExpressionProcessor;
import com.cpiinfo.iot.datasource.spring.boot.autoconfigure.druid.DruidDynamicDataSourceConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态数据源核心自动配置类
 *
 * @author TaoYu Kanyuxia
 * @see DynamicDataSourceProvider
 * @see DynamicDataSourceStrategy
 * @see DynamicRoutingDataSource
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Import(DruidDynamicDataSourceConfiguration.class)
@ConditionalOnProperty(prefix = DynamicDataSourceProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class DynamicDataSourceAutoConfiguration {

  @Autowired
  private DynamicDataSourceProperties properties;

  /**
   * 数据库健康状况检查，注入后在 MasterSlaveAutoRoutingPlugin 中使用
   *
   * @param dataSource 当前数据源
   */
  @Bean
  @ConditionalOnProperty(DynamicDataSourceProperties.HEALTH)
  @ConditionalOnMissingBean
  public DbHealthIndicator dbHealthIndicator(DataSource dataSource) {
    return new DbHealthIndicator(dataSource);
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicDataSourceProvider dynamicDataSourceProvider() {
    return new YmlDynamicDataSourceProvider(properties);
  }

  @Bean(name="dynamicDataSourceCreator")
  @ConditionalOnMissingBean
  public DynamicDataSourceCreator dynamicDataSourceCreator() {
    DynamicDataSourceCreator dynamicDataSourceCreator = new DynamicDataSourceCreator();
    dynamicDataSourceCreator.setDruidGlobalConfig(properties.getDruid());
    dynamicDataSourceCreator.setHikariGlobalConfig(properties.getHikari());
    dynamicDataSourceCreator.setGlobalPublicKey(properties.getPublicKey());
    return dynamicDataSourceCreator;
  }

  @Bean(name="dynamicRoutingDataSource")
  @ConditionalOnMissingBean
  public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
    DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
    dataSource.setPrimary(properties.getPrimary());
    dataSource.setStrategy(properties.getStrategy());
    dataSource.setProvider(dynamicDataSourceProvider);
    dataSource.setP6spy(properties.getP6spy());
    dataSource.setStrict(properties.getStrict());
    return dataSource;
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor(
      DsProcessor dsProcessor) {
    DynamicDataSourceAnnotationInterceptor interceptor = new DynamicDataSourceAnnotationInterceptor();
    interceptor.setDsProcessor(dsProcessor);
    DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(
        interceptor);
    advisor.setOrder(properties.getOrder());
    return advisor;
  }

  @Bean
  @ConditionalOnMissingBean
  public DsProcessor dsProcessor() {
    DsHeaderProcessor headerProcessor = new DsHeaderProcessor();
    DsSessionProcessor sessionProcessor = new DsSessionProcessor();
    DsSpelExpressionProcessor spelExpressionProcessor = new DsSpelExpressionProcessor();
    headerProcessor.setNextProcessor(sessionProcessor);
    sessionProcessor.setNextProcessor(spelExpressionProcessor);
    return headerProcessor;
  }

  @Bean
  @ConditionalOnBean(DynamicDataSourceConfigure.class)
  public DynamicDataSourceAdvisor dynamicAdvisor(
      DynamicDataSourceConfigure dynamicDataSourceConfigure, DsProcessor dsProcessor) {
    DynamicDataSourceAdvisor advisor = new DynamicDataSourceAdvisor(
        dynamicDataSourceConfigure.getMatchers());
    advisor.setDsProcessor(dsProcessor);
    advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return advisor;
  }
}
