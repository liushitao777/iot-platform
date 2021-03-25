package com.cpiinfo.iot.logdict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpiinfo.iot.commons.utils.Result;
import com.cpiinfo.iot.logdict.service.SysDictService;
import com.cpiinfo.iot.logdict.dto.SysDictDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName SysDictController
 * @Deacription 数据字典编码Controller
 * @Author yangbo
 * @Date 2020年09月12日 16:38
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("sysDict")
@Api(tags = "后台管理--数据字典编码管理")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @GetMapping("listSysDict")
    @ApiOperation("列表查询字典信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "字典编码类型，si.push.target-推送平台", paramType = "query", dataType = "String")
    })
    public Result<List<SysDictDTO>> listSysDictInfo(@RequestParam(name = "dictType", required = false)String dictType) {
    	List<SysDictDTO> list = sysDictService.listByDictType(dictType);
        return new Result<List<SysDictDTO>>().ok(list);
    }


}
