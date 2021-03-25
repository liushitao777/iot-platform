package com.cpiinfo.iot.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cpiinfo.iot.commons.entity.SysUser;
import com.cpiinfo.iot.commons.exception.IOTException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author ShuPF
 * @Date 2020-09-03 16:48
 */
public class SecurityUtil {

    public static SysUser getSysUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new SysUser();
        }
        Object obj = authentication.getPrincipal();
        if (obj != null) {
            JSONObject object = JSON.parseObject(JSON.toJSONString(obj));
            String str = object.getString("sysUser");
            if (StringUtils.isNotBlank(str)) {
                return JSON.parseObject(str, SysUser.class);
            }
        }

        throw new IOTException("登录失效， 请重新登录");
    }

}
