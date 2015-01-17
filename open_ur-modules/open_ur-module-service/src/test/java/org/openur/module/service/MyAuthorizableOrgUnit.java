package org.openur.module.service.security;

import java.util.Set;
import java.util.stream.Collectors;

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
	
	public Set<MyAuthorizableMember> getAuthorizableMembers()
	{
		return super.getMembers()
			.stream()
			.map(member -> (MyAuthorizableMember) member)
			.collect(Collectors.toSet()); 
	}

	@Override
	public MyAuthorizableOrgUnit getSuperOrgUnit()
	{
		return (MyAuthorizableOrgUnit) super.getSuperOrgUnit();
	}

	public void setSuperOrgUnit(MyAuthorizableOrgUnit superOrgUnit)
	{
		super.setSuperOrgUnit(superOrgUnit);
	}

	@Override
	public MyAuthorizableOrgUnit getRootOrgUnit()
	{
		return (MyAuthorizableOrgUnit) super.getRootOrgUnit();
	}

	public void setRootOrgUnit(MyAuthorizableOrgUnit rootOrgUnit)
	{
		super.setRootOrgUnit(rootOrgUnit);
	}
}
