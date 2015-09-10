package org.openur.module.service.userstructure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
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
		Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");		
		
		return orgUnitDao.findOrgUnitById(orgUnitId);
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		Validate.notEmpty(orgUnitNumber, "org-unit-number must not be empty!");		
		
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
		Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");		
		
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
