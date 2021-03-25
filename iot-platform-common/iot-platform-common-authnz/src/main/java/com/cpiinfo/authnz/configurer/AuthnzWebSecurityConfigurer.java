package com.cpiinfo.authnz.configurer;

import com.cpiinfo.authnz.mobile.CustomizeLogoutSuccessHandler;
import com.cpiinfo.authnz.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import java.util.List;
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class AuthnzWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private static final Log log = LogFactory.getLog(AuthnzWebSecurityConfigurer.class);
		@Autowired
		private UserDetailsService userDetailsService;

        @Autowired
        private AuthnzProperties properties;

		@Autowired
		private UserResourceService userResourceService;

		@Autowired
		private CustomizeLogoutSuccessHandler logoutSuccessHandler;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(daoAuthenticationProvider());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
            RequestHeaderRequestMatcher ajaxRequestMatcher = new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");
            CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler(userResourceService, properties.getIndexPage());
            CustomFormLoginConfigurer<HttpSecurity> customFormLogin = new CustomFormLoginConfigurer<HttpSecurity>();
            http.authorizeRequests()//配置路径拦截，表明路径访问所对应的权限，角色，认证信息
                    .withObjectPostProcessor(new FilterSecurityInterceptorDynamicSourceProcessor())
                    .withObjectPostProcessor(new AccessManagerProcessor())
                    // 其他请求需要认证登陆
                    .anyRequest().authenticated()
                    .and()//.denyAll()
                    //登录,配置登录拦截的URL
                    .apply(customFormLogin)
                    .loginProcessingUrl(properties.getLoginProcessingUrl()).permitAll()//.defaultSuccessUrl(indexPage)
                    .successHandler(successHandler)
                    .failureHandler(new CustomAuthenticationFailureHandler())
                    .and()
                    //对应了注销相关的配置
                    .logout().logoutSuccessHandler(logoutSuccessHandler).permitAll()
                    .and()
                    .exceptionHandling().defaultAuthenticationEntryPointFor(new HttpAjaxForbiddenEntryPoint(), ajaxRequestMatcher)
                    .accessDeniedHandler(new AjaxAwareAccessDeniedHandlerImpl())
                    .and()
                    .csrf().disable()
                    .sessionManagement()
                    .maximumSessions(1);
            if (properties.getLoginPage() != null && !"".equals(properties.getLoginPage())) {
                customFormLogin.loginPage(properties.getLoginPage()).permitAll();
            }
        }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.httpFirewall()
        web.ignoring().antMatchers("/", "/**/*/*.css", "/**/*/*.js","/**/images/**", "/favicon.png", "/actuator/**","/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/error", //异常处理
				"/swagger-ui.html"
        );

        List<String> ignoreUrls = properties.getIgnoreUrls();
        if(ignoreUrls != null && ignoreUrls.size() > 0) {
            web.ignoring().antMatchers(ignoreUrls.toArray(new String[ignoreUrls.size()]));
        }
        String loginPage = properties.getLoginPage();
        if(loginPage != null && !"".equals(loginPage)) {
            web.ignoring().antMatchers(loginPage);
        }
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }
}
