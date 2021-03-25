package com.cpiinfo.iot.mybatis.dto.personnel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName PersonnelDTO
 * @Deacription TODO 共用的人员花名册DTO，维检人员、外委人员、访问人员共用
 * @Author liwenjie
 * @Date 2020/7/30 17:19
 * @Version 1.0
 **/
@Data
@ApiModel("共用的人员花名册DTO，人员安全模块需要")
public class PersonnelDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "人员编码")
    private String pCode;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "人员类型")
    private Integer type;

    @ApiModelProperty(value = "异常次数")
    private Integer expCount;


}
