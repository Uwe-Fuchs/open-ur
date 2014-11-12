package org.openur.module.domain.security.authorization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitMember;
import org.openur.module.domain.userstructure.person.abstr.AbstractPerson;

public class AuthorizableMember
	extends AbstractOrgUnitMember
	implements IAuthorizableMember
{
	private static final long serialVersionUID = 1090300386847874882L;
	
	private final Set<OpenURRole> roles = new HashSet<>();
	
	public AuthorizableMember(AbstractPerson person, String orgUnitId)
	{
		super(person, orgUnitId);
	}

	public AuthorizableMember(AbstractPerson person, String orgUnitId, Collection<OpenURRole> roles)
	{
		super(person, orgUnitId);
		
		if (roles != null)
		{
			this.roles.addAll(roles);
		}
	}

	@Override
	public Set<OpenURRole> getRoles()
	{
		return roles;
	}
}
