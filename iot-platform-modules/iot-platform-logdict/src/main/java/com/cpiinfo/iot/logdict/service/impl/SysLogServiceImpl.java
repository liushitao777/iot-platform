package com.cpiinfo.iot.logdict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cpiinfo.iot.commons.utils.Result;
import com.cpiinfo.iot.logdict.dao.SysLogMapper;
import com.cpiinfo.iot.logdict.dto.GetPageSysLogReq;
import com.cpiinfo.iot.logdict.entity.SysLog;
import com.cpiinfo.iot.logdict.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户操作日志表 服务实现类
 * </p>
 *
 * @author ShuPF
 * @since 2021-01-04
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public Result getPageSysLog(GetPageSysLogReq req) {
        String pageNo = req.getPage();
        String size = req.getLimit();
        if ((StringUtils.isNotBlank(pageNo) && !pageNo.matches("\\d+")) ||
                (StringUtils.isNotBlank(size) && !size.matches("\\d+"))) {
            return new Result().error("分页参数异常");
        }

        int intPage = 1, intSize = 20;
        if (StringUtils.isNotBlank(pageNo)) {
            intPage = Integer.parseInt(pageNo);
        }
        if (StringUtils.isNotBlank(size)) {
            intSize = Integer.parseInt(size);
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        String userName = req.getUserName();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }
        String ip = req.getIp();
        if (StringUtils.isNotBlank(ip)) {
            queryWrapper.eq("ip", ip);
        }
        String startTime = req.getStartTime();
        String endTime = req.getEndTime();
        if (StringUtils.isNotBlank(startTime) && StringUtils.isBlank(endTime)) {
            return new Result().error("结束时间不能为空");
        } else if (StringUtils.isNotBlank(endTime) && StringUtils.isBlank(startTime)) {
            return new Result().error("开始时间不能为空");
        } else if (StringUtils.isNotBlank(endTime) && StringUtils.isNotBlank(startTime)) {
           queryWrapper.between("create_time", startTime, endTime);
        }

        queryWrapper.orderByDesc("create_time");
        IPage<SysLog> page = sysLogMapper.selectPage( new Page(intPage, intSize), queryWrapper);

        return new Result().ok(page);
    }

    @Override
    public void timingExe(int keepDays) {
        baseMapper.deleteSixMonthBeforeData(keepDays);
    }
}
