package com.cpiinfo.iot.logdict.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cpiinfo.iot.logdict.entity.SysDictEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName SysDictDto
 * @Description: TODO
 * @Author LuoWei
 * @Date 2020/7/20
 * @Version V1.0
 **/
@Data
public class SysDictDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 组件id
     */
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型，si.push.target-推送平台")
    private String dictType;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称中文")
    private String dictName;

    /**
     * 模型编号
     */
    @ApiModelProperty(value = "模型编码英文")
    private String modeCode;

    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典编码英文")
    private String dictValue;
    
    /**
     * 是否选中 0是1否
     */
    private String isSelect;
    
    List<SysDictDTO> list = new ArrayList<SysDictDTO>();
    
}
