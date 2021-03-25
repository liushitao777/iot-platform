package com.cpiinfo.iot.mybatis.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SysDictDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典编号")
    private Long id;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "模型编码")
    private String modeCode;

    @ApiModelProperty(value = "字典值")
    private String dictValue;
}
