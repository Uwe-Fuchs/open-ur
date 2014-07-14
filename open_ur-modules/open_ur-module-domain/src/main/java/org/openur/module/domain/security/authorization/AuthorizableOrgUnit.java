package org.openur.module.domain.security.authorization;

import java.util.Collection;

import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;

public class AuthorizableOrgUnit
	extends OrganizationalUnit
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = 4157616065471955134L;

	public AuthorizableOrgUnit(OrganizationalUnitBuilder b)
	{
		super(b);
	}
	
	public AuthorizableOrgUnit(OrganizationalUnitBuilder b, Collection<IAuthorizableMember> authMembers)
	{
		super(authMembers != null ? b.members(authMembers) : b);
	}
}
