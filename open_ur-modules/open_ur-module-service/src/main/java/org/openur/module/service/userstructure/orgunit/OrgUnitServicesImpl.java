package org.openur.module.service.userstructure.orgunit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

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
		return userStructureDao.findOrgUnitById(orgUnitId);
	}

	@Override
	public IOrganizationalUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		return userStructureDao.findOrgUnitByNumber(orgUnitNumber);
	}

	@Override
	public Set<IOrganizationalUnit> obtainAllOrgUnits()
	{
		List<IOrganizationalUnit> orgUnitList = userStructureDao.obtainAllOrgUnits();
		
		if (orgUnitList == null)
		{
			return new HashSet<>();
		} else
		{
			return new HashSet<>(orgUnitList);
		}
	}

	@Override
	public Set<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId,
		boolean inclMembers)
	{
		List<IOrganizationalUnit> orgUnitList = userStructureDao.obtainSubOrgUnitsForOrgUnit(
			orgUnitId, inclMembers);
		
		if (orgUnitList == null)
		{
			return new HashSet<>();
		} else
		{
			return new HashSet<>(orgUnitList);
		}
	}

	@Override
	public Set<IOrganizationalUnit> obtainRootOrgUnits()
	{
		List<IOrganizationalUnit> orgUnitList = userStructureDao.obtainRootOrgUnits();
		
		if (orgUnitList == null)
		{
			return new HashSet<>();
		} else
		{
			return new HashSet<>(orgUnitList);
		}
	}
}
