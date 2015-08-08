package org.openur.remoting.resource.userstructure;

import java.util.Set;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.service.userstructure.IOrgUnitServices;

public class OrgUnitResource
	implements IOrgUnitServices
{

	@Override
	public IAuthorizableOrgUnit findOrgUnitById(String orgUnitId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, boolean inclMembers)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
