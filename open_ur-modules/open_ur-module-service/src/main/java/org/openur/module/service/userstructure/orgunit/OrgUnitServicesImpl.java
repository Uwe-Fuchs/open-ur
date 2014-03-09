package org.openur.module.service.userstructure.orgunit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.persistence.userstructure.IUserStructureDao;

public class OrgUnitServicesImpl
	implements IOrgUnitServices
{
	private IUserStructureDao userStructureDao;

	@Inject
	public void setUserStructureDao(IUserStructureDao userStructureDao)
	{
		this.userStructureDao = userStructureDao;
	}
	
	@Override
	public IOrganizationalUnit findOrgUnitById(String orgUnitId)
	{
		IOrganizationalUnit orgUnit = userStructureDao.findOrgUnitById(orgUnitId);
		
		return orgUnit;
	}

	@Override
	public IOrganizationalUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		IOrganizationalUnit orgUnit = userStructureDao.findOrgUnitByNumber(orgUnitNumber);
		
		return orgUnit;
	}

	@Override
	public Set<IOrganizationalUnit> obtainAllOrgUnits()
	{
		Set<IOrganizationalUnit> orgUnitSet = new HashSet<>();
		
		List<IOrganizationalUnit> orgUnitList = userStructureDao.obtainAllOrgUnits();
		
		if (!CollectionUtils.isEmpty(orgUnitList))
		{
			orgUnitSet.addAll(orgUnitList);
		}
		
		return orgUnitSet;
	}

	@Override
	public Set<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId,
		boolean inclMembers)
	{
		Set<IOrganizationalUnit> orgUnitSet = new HashSet<>();
		
		List<IOrganizationalUnit> orgUnitList = userStructureDao.obtainSubOrgUnitsForOrgUnit(
			orgUnitId, inclMembers);
		
		if (!CollectionUtils.isEmpty(orgUnitList))
		{
			orgUnitSet.addAll(orgUnitList);
		}
		
		return orgUnitSet;
	}
}
