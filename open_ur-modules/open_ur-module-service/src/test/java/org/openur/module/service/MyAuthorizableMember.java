package org.openur.module.service.security;

import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.service.userstructure.orgunit.MyMember;

public class MyAuthorizableMember
	extends MyMember
	implements IAuthorizableMember
{
	private Set<MyRoleImpl> roles = new HashSet<>();
	
	public MyAuthorizableMember(IPerson person, String orgUnitId)
	{
		super(person, orgUnitId);
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
}
