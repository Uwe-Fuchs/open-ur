package org.openur.domain.testfixture.dummyimpl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.util.data.Status;

public class MyOrgUnit
	implements IOrganizationalUnit
{
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private String number;
	private MyOrgUnit superOrgUnit;
	private MyOrgUnit rootOrgUnit;
	private Set<MyOrgUnitMember> members = new HashSet<>();
	
	public MyOrgUnit(String identifier, String orgUnitNumber)
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
	public MyOrgUnit getSuperOrgUnit()
	{
		return this.superOrgUnit;
	}

	public void setSuperOrgUnit(MyOrgUnit superOrgUnit)
	{
		this.superOrgUnit = superOrgUnit;
	}

	@Override
	public MyOrgUnit getRootOrgUnit()
	{
		return this.rootOrgUnit;
	}

	public void setRootOrgUnit(MyOrgUnit rootOrgUnit)
	{
		this.rootOrgUnit = rootOrgUnit;
	}

	@Override
	public Set<MyOrgUnitMember> getMembers()
	{
		return this.members;
	}

	public void addMember(MyOrgUnitMember member)
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
