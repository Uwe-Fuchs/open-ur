package org.openur.module.service.userstructure.orgunit;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.IPerson;

public class MyOrgUnit
	implements IOrganizationalUnit
{
	private String identifier;
	private String number;
	private MyOrgUnit superOrgUnit;
	private MyOrgUnit rootOrgUnit;
	private Set<MyMember> members = new HashSet<>();

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
	public String getOrgUnitNumber()
	{
		return getNumber();
	}

	@Override
	public MyOrgUnit getSuperOrgUnit()
	{
		return this.superOrgUnit;
	}

	protected void setSuperOrgUnit(MyOrgUnit superOrgUnit)
	{
		this.superOrgUnit = superOrgUnit;
	}

	@Override
	public boolean isRootOrgUnit()
	{
		return (this.rootOrgUnit == null);
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
	public Set<MyMember> getMembers()
	{
		return this.members;
	}

	public void addMember(MyMember member)
	{
		this.getMembers().add(member);
	}

	@Override
	public IOrgUnitMember findMember(String id)
	{
    if (id == null)
    {
      return null;
    }

    for (MyMember m : this.getMembers())
    {
      if (id.equals(m.getPerson().getIdentifier()))
      {
        return m;
      }
    }

    return null;
	}

	@Override
	public boolean isMember(String id)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMember(IPerson person)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IOrgUnitMember findMember(IPerson person)
	{
		// TODO Auto-generated method stub
		return null;
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
