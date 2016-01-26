package org.openur.domain.testfixture.dummyimpl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.util.data.Status;

public class MyAuthorizableOrgUnit
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private String number;
	private MyAuthorizableOrgUnit superOrgUnit;
	private MyAuthorizableOrgUnit rootOrgUnit;
	private Set<MyAuthorizableMember> members = new HashSet<>();
	
	public MyAuthorizableOrgUnit(String identifier, String orgUnitNumber)
	{
		super();
		
		this.identifier = identifier;
		this.number = orgUnitNumber;
	}

	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}

	@Override
	public String getNumber()
	{
		return this.number;
	}

	@Override
	public MyAuthorizableOrgUnit getSuperOrgUnit()
	{
		return this.superOrgUnit;
	}

	public void setSuperOrgUnit(MyAuthorizableOrgUnit superOrgUnit)
	{
		this.superOrgUnit = superOrgUnit;
	}

	@Override
	public MyAuthorizableOrgUnit getRootOrgUnit()
	{
		return this.rootOrgUnit;
	}

	public void setRootOrgUnit(MyAuthorizableOrgUnit rootOrgUnit)
	{
		this.rootOrgUnit = rootOrgUnit;
	}

	@Override
	public Set<MyAuthorizableMember> getMembers()
	{
		return this.members;
	}

	public void addMember(MyAuthorizableMember member)
	{
		this.members.add(member);
	}

	@Override
	public Status getStatus()
	{
		return null;
	}

	@Override
	public LocalDateTime getLastModifiedDate()
	{
		return null;
	}

	@Override
	public LocalDateTime getCreationDate()
	{
		return null;
	}
}
