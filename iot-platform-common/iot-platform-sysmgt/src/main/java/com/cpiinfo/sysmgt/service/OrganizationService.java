package com.cpiinfo.sysmgt.service;

import com.cpiinfo.sysmgt.entity.Organization;
import com.cpiinfo.sysmgt.entity.ZNode;
import com.cpiinfo.sysmgt.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Title: IOrganizationService.java
 * @Package com.cpiinfo.iisp.manager.intf
 * @Description: TODO(定义单位的Service层)
 * @author 牛棚
 * @date 2016年9月13日 上午9:33:38
 * @version V1.0
 */
public interface OrganizationService {
	Result getOrgTree();

	Result insertOrg(Organization org);

	Result deleteById(String id);

	Result updateOrg(Organization org);

	List<ZNode> queryUserAllOrg(String userCode);

	Result queryOrgById(String id);

    Result bulkImportOrganization(MultipartFile mfile);
}
