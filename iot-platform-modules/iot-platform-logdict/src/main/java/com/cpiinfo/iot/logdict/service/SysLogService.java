package com.cpiinfo.iot.logdict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cpiinfo.iot.commons.utils.Result;
import com.cpiinfo.iot.logdict.dto.GetPageSysLogReq;
import com.cpiinfo.iot.logdict.entity.SysLog;

/**
 * <p>
 * 系统用户操作日志表 服务类
 * </p>
 *
 * @author ShuPF
 * @since 2021-01-04
 */
public interface SysLogService extends IService<SysLog> {

    /**
     *  分页获取日志列表
     * @param req 参数
     * @return 数据
     */
    Result getPageSysLog(GetPageSysLogReq req);

    /**
     *  定时执行--删除六个月以前的数据
     */
    void timingExe(int keepDays);
}
