package com.cpiinfo.iot.logdict.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author ShuPF
 * @Date 2021-01-04 14:50
 */
@Data
public class GetPageSysLogReq {

    @ApiModelProperty(value = "当前页码， 默认1")
    private String page = "1";

    @ApiModelProperty(value = "每页显示条数， 默认20")
    private String limit = "20";

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
