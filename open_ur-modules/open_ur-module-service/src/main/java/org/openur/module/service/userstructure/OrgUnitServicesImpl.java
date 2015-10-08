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
	public IAuthorizableOrgUnit findOrgUnitById(String orgUnitId, Boolean inclMembersRoles)
	{
		Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");
		
		IAuthorizableOrgUnit ou = null;
		
		if (inclMembersRoles)
		{
			ou = orgUnitDao.findOrgUnitAndMembersById(orgUnitId);
		} else
		{
			ou = orgUnitDao.findOrgUnitById(orgUnitId);
		}
		
		return ou;
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber, Boolean inclMembersRoles)
	{
		Validate.notEmpty(orgUnitNumber, "org-unit-number must not be empty!");		
		
		IAuthorizableOrgUnit ou = null;
		
		if (inclMembersRoles)
		{
			ou = orgUnitDao.findOrgUnitAndMembersByNumber(orgUnitNumber);
		} else
		{
			ou = orgUnitDao.findOrgUnitByNumber(orgUnitNumber);
		}
		
		return ou;
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
		Boolean inclMembersRoles)
	{
		Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");		
		
		List<IAuthorizableOrgUnit> orgUnitList;
		
		if (inclMembersRoles)
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
