package org.openur.module.domain.security.orgunit;

import java.util.Collection;

import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.user.person.IPerson;

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

	// operations:
	// TODO: use interface-default-method in Java8
	@Override
	public IAuthorizableMember findAuthorizableMember(String id)
	{
		return (IAuthorizableMember) super.findMember(id);
	}
	
	// TODO: use interface-default-method in Java8
	@Override
	public boolean hasPermission(IPerson person, IApplication app, IPermission permission)
	{
		IAuthorizableMember member = findAuthorizableMember(person.getIdentifier());
		
		if (member == null)
		{
			return false;
		}
		
		return member.hasPermission(app, permission);
	}
}
