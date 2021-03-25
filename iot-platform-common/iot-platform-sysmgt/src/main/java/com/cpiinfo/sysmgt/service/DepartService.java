package com.cpiinfo.sysmgt.service;

import com.cpiinfo.sysmgt.dto.DepartTreeDTO;
import com.cpiinfo.sysmgt.entity.Depart;
import com.cpiinfo.sysmgt.utils.Result;

import java.util.List;

/**
* @Title: IUserService.java 
* @Package com.cpiinfo.iisp.manager.intf
* @Description: TODO(用户的UserService层) 
* @author 程志宏
* @date 2016年10月14日 下午8:30:04 
* @version V1.0   
*/
public interface DepartService {
    List<DepartTreeDTO> getDepartTreeByOrgCode(String orgCode);

    Result addDepart(Depart depart);

    Result deleteDepartById(String id);

    Result updateDepart(Depart depart);

    Result queryDepartById(String id);
}
