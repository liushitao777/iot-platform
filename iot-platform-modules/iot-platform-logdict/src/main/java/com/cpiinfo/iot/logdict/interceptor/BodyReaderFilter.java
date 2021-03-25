package com.cpiinfo.iot.logdict.interceptor;

import com.alibaba.fastjson.JSON;
import com.cpiinfo.iot.commons.entity.SysUser;
import com.cpiinfo.iot.commons.utils.IpUtils;
import com.cpiinfo.iot.commons.utils.SecurityUtil;
import com.cpiinfo.iot.logdict.entity.SysLog;
import com.cpiinfo.iot.logdict.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.XmlServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Author ShuPF
 * @Date 2021-01-06 11:17
 */
public class BodyReaderFilter implements Filter {

    private Logger logger = LogManager.getLogger(BodyReaderFilter.class);

    private static Map<String, String> uriToName = null;

    private ApplicationContext context;

    public BodyReaderFilter() {
        super();
    }


    public BodyReaderFilter( ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        logger.info(">>>>>>>>>>>>>>>>>> request：{}", httpServletRequest.getRequestURL());

        MyRequestWrapper requestWrapper=new MyRequestWrapper(httpServletRequest);
        //MyResponseWrapper myResponseWrapper = new MyResponseWrapper(resp);
        chain.doFilter(requestWrapper, response);

        Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
        String body = requestWrapper.getBody();
        if (StringUtils.isNotBlank(body)) {
            Map<String, Object> bodyParams = null;
            try {
                bodyParams = JSON.parseObject(body, Map.class);
                params.putAll(bodyParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String uri = httpServletRequest.getRequestURI();
        if (uri.equals("/") || "/error".equals(uri) || "/loadUserMenus".equals(uri) || uri.equals("/robot/notify")
                || "/getButtonByMenusUrl".equals(uri) || uri.contains("swagger") || uri.equals("/fireAlarm/notify") || uri.equals("/favicon.ico")
                || uri.contains(".js") || uri.matches("/^https?:\\/\\/(.+\\/)+.+(\\.(gif|png|jpg|jpeg|webp|svg|psd|bmp|tif))$/i")) {
            return;
        }

        String result = "";
//        byte[] bytes = myResponseWrapper.getBytes(); // 获取缓存的响应数据
//        if (bytes != null) {
//            result = new String(bytes,"utf-8");
//            logger.info(">>>>>>>>>>>>>>>>>> response：{}", result);
//            resp.getOutputStream().write(bytes); // 将压缩数据响应给客户端
//        }

        String nmae = "";
        if (uriToName == null || (nmae = uriToName.get(uri)) == null) {
            uriToName = this.getUriToNameMapping();
            nmae = uriToName.get(uri);
        }

        String ip = IpUtils.getIpAddr(httpServletRequest);
        String way = httpServletRequest.getMethod();
        SysLog sysLog = new SysLog();
        sysLog.setName(nmae);
        sysLog.setResult(result);
        sysLog.setIp(ip);
        sysLog.setUri(uri);
        sysLog.setWay(way);
        if (params.size() > 0) {
            sysLog.setParams(JSON.toJSONString(params));
        }
        sysLog.setCreateTime(new Date());
        SysUser sysUser = null;
        try {
            sysUser = SecurityUtil.getSysUser();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (sysUser != null) {
            sysLog.setUserName(StringUtils.isNotBlank(sysUser.getUserName()) ? sysUser.getUserName() : sysUser.getUserCode());
        }
        sysLog.insert();
    }

    private Map<String, String> getUriToNameMapping() {
        Map<String, String> result = new HashMap<>();
        Map<String, Object> map = context.getBeansWithAnnotation(Controller.class);
        Collection<Object> beans = map.values();
        for (Object bean : beans) {
            Class<?> clz = bean.getClass();
            RequestMapping controller = AnnotationUtils.getAnnotation(clz, RequestMapping.class);
            Api api  = AnnotationUtils.getAnnotation(clz, Api.class);
            String cname = "";
            if (api != null) {
                String[] tags = api.tags();
                cname = tags[0];
            }

            String values = "";
            if (controller != null) {
                String[] cValues =  controller.value();
                if (cValues.length > 0) {
                    values = cValues[0];
                }
            }


            // 获取所有 有 ApiOperation 注解的方法
            List<Method> apiMethods = MethodUtils.getMethodsListWithAnnotation(clz, ApiOperation.class);
            for (Method method : apiMethods) {
                ApiOperation piAnno = AnnotationUtils.getAnnotation(method, ApiOperation.class);
                String name = cname;
                if (piAnno != null) {
                    String oper = piAnno.value();
                    name = name + "【" + oper + "】";
                }

                String url = "";
                RequestMapping requestMapping = AnnotationUtils.getAnnotation(method, RequestMapping.class);
                if (StringUtils.isBlank(url) && requestMapping != null) {
                    String[] mValues = requestMapping.value();
                    if (mValues.length > 0) {
                        url = requestMapping.value()[0];
                    }
                }
                GetMapping getMapping = AnnotationUtils.getAnnotation(method, GetMapping.class);
                if (StringUtils.isBlank(url) && getMapping != null) {
                    String[] mValues = getMapping.value();
                    if (mValues.length > 0) {
                        url = getMapping.value()[0];
                    }
                }
                PostMapping postMapping = AnnotationUtils.getAnnotation(method, PostMapping.class);
                if (StringUtils.isBlank(url) && postMapping != null) {
                    String[] mValues = postMapping.value();
                    if (mValues.length > 0) {
                        url = postMapping.value()[0];
                    }
                }
                DeleteMapping deleteMapping = AnnotationUtils.getAnnotation(method, DeleteMapping.class);
                if (StringUtils.isBlank(url) && deleteMapping != null) {
                    String[] mValues = deleteMapping.value();
                    if (mValues.length > 0) {
                        url = deleteMapping.value()[0];
                    }
                }
                PutMapping putMapping =  AnnotationUtils.getAnnotation(method, PutMapping.class);
                if (StringUtils.isBlank(url) && putMapping != null) {
                    String[] mValues = putMapping.value();
                    if (mValues.length > 0) {
                        url = putMapping.value()[0];
                    }
                }

                url = values.endsWith("/") ? (values + url) : (values + "/" + url);
                url = url.startsWith("/") ? url : "/" + url;
                result.put(url, name);
            }
        }

        return result;
    }

}
