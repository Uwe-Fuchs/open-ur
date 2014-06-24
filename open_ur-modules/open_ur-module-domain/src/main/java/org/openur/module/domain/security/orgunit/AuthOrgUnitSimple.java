package org.openur.module.domain.security.orgunit;

import java.util.Collection;

import org.openur.module.domain.userstructure.orgunit.OrgUnitSimple;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimpleBuilder;

public class AuthOrgUnitSimple
	extends OrgUnitSimple
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = -9115069249614557685L;

	public AuthOrgUnitSimple(OrgUnitSimpleBuilder b)
	{
		super(b);
	}
	
	public AuthOrgUnitSimple(OrgUnitSimpleBuilder b, Collection<IAuthorizableMember> authMembers)
	{
		super(authMembers != null ? b.members(authMembers) : b);
	}
}
