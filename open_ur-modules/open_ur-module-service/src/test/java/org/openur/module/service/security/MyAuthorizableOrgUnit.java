package org.openur.module.service.security;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.service.userstructure.orgunit.MyOrgUnit;

public class MyAuthorizableOrgUnit
	extends MyOrgUnit
	implements IAuthorizableOrgUnit
{
	public MyAuthorizableOrgUnit(String identifier, String number)
	{
		super(identifier, number);
	}

	public void addMember(MyAuthorizableMember member)
	{
		super.addMember(member);
	}

	@Override
	public MyAuthorizableOrgUnit getSuperOu()
	{
		return (MyAuthorizableOrgUnit) super.getSuperOu();
	}

	@Override
	public MyAuthorizableOrgUnit getRootOrgUnit()
	{
		return (MyAuthorizableOrgUnit) super.getRootOrgUnit();
	}

	public void setSuperOrgUnit(MyAuthorizableOrgUnit superOrgUnit)
	{
		super.setSuperOrgUnit(superOrgUnit);
	}

	public void setRootOrgUnit(MyAuthorizableOrgUnit rootOrgUnit)
	{
		super.setRootOrgUnit(rootOrgUnit);
	}
}
