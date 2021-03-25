package com.cpiinfo.iot.logdict.service;/*



/**
 * @ClassName SysDictService
 * @Description: TODO
 * @Author LuoWei
 * @Date 2020/7/20
 * @Version V1.0
 **/

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cpiinfo.iot.mybatis.service.BaseService;
import com.cpiinfo.iot.logdict.dto.SysDictDTO;
import com.cpiinfo.iot.logdict.entity.SysDictEntity;

/**
 * @MethodName:
 * @Description: TODO
 * @Param: 
 * @Return: 
 * @Author: luowei
 * @Date: 2020/8/13
**/
public interface SysDictService extends BaseService<SysDictEntity> {
    List<SysDictEntity> selectList(QueryWrapper<SysDictEntity> queryWrapper);
    
    List<SysDictDTO> listByDictType(String dictType);
}
