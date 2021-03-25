package com.cpiinfo.sysmgt.dao;

import java.util.List;

import com.cpiinfo.sysmgt.entity.Organization;


public interface OrgUnitInfoMapper {
	public int updateOrgUnitInfo(Organization orgInfo);
	public int updateParentUnitInfo(Organization org);
	public Organization selectUnitInfo(String orgCode);
	public List<Organization> getAllParent(String orgCode);
	public List<Organization> getPlateCapacity();
}