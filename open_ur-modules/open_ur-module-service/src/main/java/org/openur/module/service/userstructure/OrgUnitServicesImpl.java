package org.openur.module.service.userstructure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.persistence.dao.IOrgUnitDao;

public class OrgUnitServicesImpl
	implements IOrgUnitServices
{
	@Inject
	private IOrgUnitDao orgUnitDao;
	
	@Override
	public IAuthorizableOrgUnit findOrgUnitById(String orgUnitId)
	{
		return orgUnitDao.findOrgUnitById(orgUnitId);
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		return orgUnitDao.findOrgUnitByNumber(orgUnitNumber);
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		List<IAuthorizableOrgUnit> orgUnitList = orgUnitDao.obtainAllOrgUnits();
		
		if (orgUnitList == null)
		{
			return new HashSet<>();
		} else
		{
			return new HashSet<>(orgUnitList);
		}
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId,
		boolean inclMembers)
	{
		List<IAuthorizableOrgUnit> orgUnitList;
		
		if (inclMembers)
		{
			orgUnitList = orgUnitDao.obtainSubOrgUnitsForOrgUnitInclMembers(orgUnitId);
		} else
		{
			orgUnitList = orgUnitDao.obtainSubOrgUnitsForOrgUnit(orgUnitId);
		}
		
		if (orgUnitList == null)
		{
			return new HashSet<>();
		} else
		{
			return new HashSet<>(orgUnitList);
		}
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		List<IAuthorizableOrgUnit> orgUnitList = orgUnitDao.obtainRootOrgUnits();
		
		if (orgUnitList == null)
		{
			return new HashSet<>();
		} else
		{
			return new HashSet<>(orgUnitList);
		}
	}
}
