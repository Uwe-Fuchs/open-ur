package org.openur.module.service.userstructure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.persistence.dao.IUserStructureDao;

public class OrgUnitServicesImpl
	implements IOrgUnitServices
{
	@Inject
	private IUserStructureDao userStructureDao;
	
	@Override
	public IAuthorizableOrgUnit findOrgUnitById(String orgUnitId)
	{
		return userStructureDao.findOrgUnitById(orgUnitId);
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		return userStructureDao.findOrgUnitByNumber(orgUnitNumber);
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		List<IAuthorizableOrgUnit> orgUnitList = userStructureDao.obtainAllOrgUnits();
		
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
		List<IAuthorizableOrgUnit> orgUnitList = userStructureDao.obtainSubOrgUnitsForOrgUnit(
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
	public Set<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		List<IAuthorizableOrgUnit> orgUnitList = userStructureDao.obtainRootOrgUnits();
		
		if (orgUnitList == null)
		{
			return new HashSet<>();
		} else
		{
			return new HashSet<>(orgUnitList);
		}
	}
}
