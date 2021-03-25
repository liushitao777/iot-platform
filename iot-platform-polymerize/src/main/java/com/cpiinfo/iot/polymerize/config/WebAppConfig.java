package com.cpiinfo.iot.polymerize.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.cpiinfo.iot.polymerize.interceptor.SwitchDataSourceInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @ClassName WebAppConfig
 * @Deacription TODO web相关配置
 * @Author liwenjie
 * @Date 2020/7/19 8:52
 * @Version 1.0
 **/
@EnableWebMvc
@Configuration("platform-webAppConfig")
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private SwitchDataSourceInterceptor switchDataSourceInterceptor;

    @Value("${com.cpiinfo.iot.static.access-path:}")
    private String accessPath;
    @Value("${com.cpiinfo.iot.static.folder:}")
    private String folder;

    //设置接口连接超时时间
    @Override
    public  void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setDefaultTimeout(10000);
        configurer.registerCallableInterceptors(timeoutInterceptor());
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    @Bean
    public FilterRegistrationBean<SwitchDataSourceInterceptor> testFilterRegistration() {
        FilterRegistrationBean<SwitchDataSourceInterceptor> registration = new FilterRegistrationBean<SwitchDataSourceInterceptor>(switchDataSourceInterceptor);
        registration.addUrlPatterns(switchDataSourceInterceptor.getLoginProcessingUrl());
        registration.setOrder(Integer.MIN_VALUE);
        registration.setName("switchDataSourceFilter");
        return registration;
    }
    

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //加入的顺序就是拦截器执行的顺序
        registry.addInterceptor(switchDataSourceInterceptor)
                //一个*：只匹配字符，不匹配路径（/）
                //两个**：匹配字符，和路径（/）
                //排除不需要拦截的请求
                .excludePathPatterns(
                        "/login",
                        "/swagger/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html/**",
                        "/statics/**",
                        "/favicon.ico"
                )
                .addPathPatterns("/**");
    }

    /**
     * 配置文件访问虚拟路径映射物理路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (accessPath != null && !"".equals(accessPath)) {
            registry.addResourceHandler(accessPath).addResourceLocations("file:" + folder);
        }

        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }

    /**
     * 解决后端返回前端long类型精度丢失
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }
}
