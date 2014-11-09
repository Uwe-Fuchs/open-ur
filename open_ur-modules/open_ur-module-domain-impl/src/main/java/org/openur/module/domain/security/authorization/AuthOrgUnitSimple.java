package org.openur.module.domain.security.authorization;

import java.util.Collection;
import java.util.HashSet;

import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimple;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimpleBuilder;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;

public class AuthOrgUnitSimple
	extends OrgUnitSimple
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = -9115069249614557685L;

	private AuthOrgUnitSimple(AuthOrgUnitSimpleBuilder b)
	{
		super(b);
	}

	// accessors:
	@Override
	public AuthOrgUnitSimple getRootOrgUnit()
	{
		return (AuthOrgUnitSimple) super.rootOrgUnit;
	}

	@Override
	public AuthOrgUnitSimple getSuperOu()
	{
		return (AuthOrgUnitSimple) super.superOrgUnit;
	}
	
	public static class AuthOrgUnitSimpleBuilder extends OrgUnitSimpleBuilder
	{
		// constructors:
		public AuthOrgUnitSimpleBuilder()
		{
			super();
		}

		public AuthOrgUnitSimpleBuilder(String identifier)
		{
			super(identifier);
		}

		public AuthOrgUnitSimpleBuilder(OrgUnitSimple rootOrgUnit)
		{
			super(rootOrgUnit);
		}

		public AuthOrgUnitSimpleBuilder(String identifier, 	OrgUnitSimple rootOrgUnit)
		{
			super(identifier, rootOrgUnit);
		}

		// accessors:
		public AuthOrgUnitSimpleBuilder authorizableMembers(Collection<AuthorizableMember> authMembers)
		{
			super.members(new HashSet<OrgUnitMember>(authMembers));
			return this;
		}
		
		@Override
		public AuthOrgUnitSimpleBuilder superOrgUnit(AbstractOrgUnit superOrgUnit)
		{
			super.superOrgUnit(superOrgUnit);
			return this;
		}

		// builder:
		@Override
		public AuthOrgUnitSimple build()
		{
			super.build();
			
			return new AuthOrgUnitSimple(this);
		}
	}
}
