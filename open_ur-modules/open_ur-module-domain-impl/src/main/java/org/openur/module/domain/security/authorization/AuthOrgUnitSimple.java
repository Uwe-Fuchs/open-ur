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

		public AuthOrgUnitSimpleBuilder(AbstractOrgUnit rootOrgUnit)
		{
			super(rootOrgUnit);
		}

		public AuthOrgUnitSimpleBuilder(String identifier, 	AbstractOrgUnit rootOrgUnit)
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
		public AuthOrgUnitSimpleBuilder superOuId(String superOuId)
		{
			super.superOuId(superOuId);
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
