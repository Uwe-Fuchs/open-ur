package org.openur.domain.impl.test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.domain.userstructure.person.IPerson;

public class MyAuthorizableMember
	implements IAuthorizableMember
{
	private IPerson person;
	private String orgUnitId;
	private Set<MyRoleImpl> roles = new HashSet<>();
	
	public MyAuthorizableMember(IPerson person, String orgUnitId)
	{
		super();
		
		this.person = person;
		this.orgUnitId = orgUnitId;
	}

	@Override
	public IPerson getPerson()
	{
		return this.person;
	}

	@Override
	public String getOrgUnitId()
	{
		return this.orgUnitId;
	}

	@Override
	public Set<MyRoleImpl> getRoles()
	{
		return roles;
	}
	
	public void addRole(MyRoleImpl role)
	{
		getRoles().add(role);
	}

	@Override
	public String getIdentifier()
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
