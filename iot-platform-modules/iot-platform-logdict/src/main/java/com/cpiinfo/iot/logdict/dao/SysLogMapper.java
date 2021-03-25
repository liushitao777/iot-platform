package com.cpiinfo.iot.logdict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cpiinfo.iot.logdict.entity.SysLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户操作日志表 Mapper 接口
 * </p>
 *
 * @author ShuPF
 * @since 2021-01-04
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    @Delete("delete from sys_log \n" +
            "where date_format(create_time,'%Y-%m-%d') <= date_format(DATE_SUB(curdate(), INTERVAL #{keepDays} DAY),'%Y-%m-%d')")
    int deleteSixMonthBeforeData(int keepDays);

}
