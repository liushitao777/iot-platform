package com.cpiinfo.sysmgt.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName CableExcleDTO
 * @Description: 电缆头导入设备表模板
 * @Author luowei
 * @Date 2020-12-10
 * @Version V1.0
 **/
@Data
public class OrganizationExcelDTO {


    @Excel(name = "组织机构名称", orderNum = "0")
    private String orgName;

    @Excel(name = "组织机构代码", orderNum = "1" ,replace = {"光纤式_3", "贴片式_35","低压侧_37"})
    private String orgCode;

    @Excel(name = "上级组织机构代码(没有为0)", orderNum = "2")
    private String parentCode;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @Excel(name = "机构建立时间", orderNum = "3")
    private Date establishTime;

    @Excel(name = "地区", orderNum = "4")
    private String location;

    @Excel(name = "联系人", orderNum = "5")
    private String linkman;

    @Excel(name = "电话", orderNum = "6")
    private String telephone;

    @Excel(name = "注册资本(万元)", orderNum = "7")
    private String registeredCapital;

    @Excel(name = "法定代表人", orderNum = "8",replace = {"高压_0", "低压_1"})
    private String legalRepresentative;

    @Excel(name = "备注", orderNum = "9")
    private String introText;


}
