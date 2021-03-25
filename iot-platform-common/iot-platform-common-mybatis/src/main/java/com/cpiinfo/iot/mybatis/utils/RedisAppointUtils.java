package com.cpiinfo.iot.mybatis.utils;

import com.cpiinfo.iot.commons.utils.ConvertUtils;
import com.cpiinfo.iot.mybatis.dto.dict.SysDictDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisAppointUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @return
     */
    public SysDictDTO lGet(String key, String dictType) {
        try {
            if (dictType == null)
                return null;
            List<Object> objects = redisTemplate.opsForList().range(key, 0, -1);
            List<SysDictDTO> dtoList = ConvertUtils.sourceToTarget(objects, SysDictDTO.class);
            if (dtoList != null)
                for (SysDictDTO dto : dtoList) {
                    if (dictType.equals(dto.getDictType())) {
                        return dto;
                    }
                }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}