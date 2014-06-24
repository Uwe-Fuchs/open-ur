package org.openur.module.domain.security.orgunit;

import java.util.Collection;

import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;

public class AuthOrganizationalUnit
	extends OrganizationalUnit
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = 4157616065471955134L;

	public AuthOrganizationalUnit(OrganizationalUnitBuilder b)
	{
		super(b);
	}
	
	public AuthOrganizationalUnit(OrganizationalUnitBuilder b, Collection<IAuthorizableMember> authMembers)
	{
		super(authMembers != null ? b.members(authMembers) : b);
	}
}
