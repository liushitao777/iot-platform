package com.cpiinfo.iot.logdict.service.impl;/*
/**
 * @ClassName SysDictServiceImpl
 * @Description: TODO
 * @Author LuoWei
 * @Date 2020/7/20
 * @Version V1.0
 **/

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cpiinfo.iot.commons.utils.ConvertUtils;
import com.cpiinfo.iot.mybatis.service.impl.BaseServiceImpl;
import com.cpiinfo.iot.logdict.dao.SysDictMapper;
import com.cpiinfo.iot.logdict.dto.SysDictDTO;
import com.cpiinfo.iot.logdict.entity.SysDictEntity;
import com.cpiinfo.iot.logdict.service.SysDictService;

@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDictEntity> implements SysDictService {
    @Override
    public List<SysDictEntity> selectList(QueryWrapper<SysDictEntity> queryWrapper) {
        return baseDao.selectList(queryWrapper);
    }

	@Override
	public List<SysDictDTO> listByDictType(String dictType) {
		QueryWrapper<SysDictEntity> queryWrapper = new QueryWrapper<SysDictEntity>();
		if(dictType != null && !"".equals(dictType)) {
			queryWrapper.eq("dict_type", dictType);
		}
		List<SysDictEntity> entityList = baseDao.selectList(queryWrapper);
		return ConvertUtils.sourceToTarget(entityList, SysDictDTO.class);
	}
}

