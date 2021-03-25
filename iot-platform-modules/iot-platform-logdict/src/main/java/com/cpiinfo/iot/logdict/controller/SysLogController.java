package com.cpiinfo.iot.logdict.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cpiinfo.iot.commons.utils.ExcelUtils;
import com.cpiinfo.iot.commons.utils.Result;
import com.cpiinfo.iot.logdict.dto.GetPageSysLogReq;
import com.cpiinfo.iot.logdict.entity.SysLog;
import com.cpiinfo.iot.logdict.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户操作日志表 服务类
 * </p>
 *
 * @author ShuPF
 * @since 2021-01-04
 */
@RestController
@RequestMapping(value = "sysLog")
@Api(tags = "后台管理--系统日志")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @ApiOperation("分页获取系统日志信息")
    @GetMapping(value = "list")
    public Result<IPage<SysLog>> getPageSysLog(GetPageSysLogReq req) {
        return sysLogService.getPageSysLog(req);
    }

    @ApiOperation("导出系统日志信息")
    @GetMapping(value = "exportSysLog")
    public void exportSysLog(GetPageSysLogReq req, HttpServletResponse response) throws Exception {
        req.setPage("1");
        req.setLimit("10000000");
        Result<IPage<SysLog>> pageResult = sysLogService.getPageSysLog(req);
        List<SysLog> logList = pageResult.getData().getRecords();
        String date = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
        ExcelUtils.exportExcelToTarget(response, date + "_系统日志", logList, SysLog.class);
    }

    public static void main(String[] args) {
        //获取clz类上有 ApiOperation 注解的所有方法
//        List<Method> apiMethods = MethodUtils.getMethodsListWithAnnotation(SysLogController.class.getSuperclass(), ApiOperation.class);
//        apiMethods.forEach(method -> {
//            ApiOperation rpAnno = AnnotationUtils.getAnnotation(method, ApiOperation.class);
//            String value = rpAnno.value();
//            System.out.println(value);
//        });
    }

}
