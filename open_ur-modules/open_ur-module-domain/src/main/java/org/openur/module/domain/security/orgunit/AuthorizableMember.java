package org.openur.module.domain.security.orgunit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.security.IRole;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.user.person.IPerson;

public class AuthorizableMember
	extends OrgUnitMember
	implements IAuthorizableMember
{
	private static final long serialVersionUID = 1090300386847874882L;
	
	private final Set<IRole> roles = new HashSet<IRole>();
	
	public AuthorizableMember(IPerson person, String orgUnitId)
	{
		super(person, orgUnitId);
	}

	public AuthorizableMember(IPerson person, String orgUnitId, Collection<IRole> roles)
	{
		super(person, orgUnitId);
		
		if (roles != null)
		{
			this.roles.addAll(roles);
		}
	}

	@Override
	public Set<IRole> getRoles()
	{
		return roles;
	}
}
