package com.cpiinfo.iot.logdict.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统用户操作日志表
 * </p>
 *
 * @author ShuPF
 * @since 2021-01-04
 */
@Data
@Accessors(chain = true)
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 用户
     */
    @Excel(name = "用户",width = 16)
    @TableField("user_name")
    private String userName;
    /**
     * IP地址
     */
    @Excel(name = "IP", width = 12)
    @TableField("ip")
    private String ip;
    /**
     * 请求方式
     */
    @Excel(name = "方式", width = 12)
    @TableField("way")
    private String way;

    /**
     * 接口名称
     */
    @Excel(name = "接口名称", width = 58)
    @TableField("name")
    private String name;
    /**
     * 请求路径
     */
    @Excel(name = "路径", width = 58)
    @TableField("uri")
    private String uri;
    /**
     * 参数
     */
    @Excel(name = "参数", width = 88)
    @TableField("params")
    private String params;

    @Excel(name = "返回值", width = 168)
    @TableField("result")
    private String result;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    @Excel(name = "时间", width = 20)
    @TableField(exist = false)
    private String time;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getTime() {
        if (createTime != null) {
            return DateUtil.format(createTime, DatePattern.NORM_DATETIME_PATTERN);
        }
        return time;
    }
}
