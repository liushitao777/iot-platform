package com.cpiinfo.iot.logdict.dao;

import com.cpiinfo.iot.mybatis.dao.BaseDao;
import com.cpiinfo.iot.logdict.entity.SysDictEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName SysDictMapper
 * @Description: TODO
 * @Author LuoWei
 * @Date 2020/7/20
 * @Version V1.0
 **/
@Mapper
public interface SysDictMapper extends BaseDao<SysDictEntity> {
    @Select("select dict_name from sys_dict where id = #{dictId}")
    String selectDictNameById(long dictId);
}


