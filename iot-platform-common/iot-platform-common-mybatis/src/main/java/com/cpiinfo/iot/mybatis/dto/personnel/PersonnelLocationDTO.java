package com.cpiinfo.iot.mybatis.dto.personnel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @ClassName PersonnelLocationDTO
 * @Deacription TODO 人员实时信息DTO
 * @Author liwenjie
 * @Date 2020/7/31 16:35
 * @Version 1.0
 **/
@Data
@ApiModel("人员实时信息DTO")
public class PersonnelLocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "人员编码")
    private String pCode;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "人员类型 1.外委人员2.访客 3.维检人员")
    private Integer type;

    @ApiModelProperty(value = "组织机构名称")
    private String orgName;

    @ApiModelProperty(value = "任务类型")
    private String taskType;

    @ApiModelProperty(value = "头像地址")
    private String headPath;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "经度")
    private Double lng;

    @ApiModelProperty("是否在线 0:不在线 1:在线")
    private Integer onLine =0;
}
