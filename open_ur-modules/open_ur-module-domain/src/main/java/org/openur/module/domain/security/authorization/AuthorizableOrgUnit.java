package org.openur.module.domain.security.authorization;

import java.util.Collection;
import java.util.HashSet;

import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;

public class AuthorizableOrgUnit
	extends OrganizationalUnit
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = 4157616065471955134L;

	private AuthorizableOrgUnit(AuthorizableOrgUnitBuilder b)
	{
		super(b);
	}
	
	public static class AuthorizableOrgUnitBuilder extends OrganizationalUnitBuilder
	{
		// constructors:
		public AuthorizableOrgUnitBuilder()
		{
			super();
		}

		public AuthorizableOrgUnitBuilder(String identifier)
		{
			super(identifier);
		}

		public AuthorizableOrgUnitBuilder(AbstractOrgUnit rootOrgUnit)
		{
			super(rootOrgUnit);
		}

		public AuthorizableOrgUnitBuilder(String identifier, 	AbstractOrgUnit rootOrgUnit)
		{
			super(identifier, rootOrgUnit);
		}

		// accessors:
		public AuthorizableOrgUnitBuilder authorizableMembers(Collection<AuthorizableMember> authMembers)
		{
			super.members(new HashSet<OrgUnitMember>(authMembers));
			return this;
		}
		
		@Override
		public AuthorizableOrgUnitBuilder name(String name)
		{
			super.name(name);
			return this;
		}

		@Override
		public AuthorizableOrgUnitBuilder shortName(String shortName)
		{
			super.shortName(shortName);
			return this;
		}

		@Override
		public AuthorizableOrgUnitBuilder description(String description)
		{
			super.description(description);
			return this;
		}

		@Override
		public AuthorizableOrgUnitBuilder address(Address address)
		{
			super.address(address);
			return this;
		}

		@Override
		public AuthorizableOrgUnitBuilder emailAdress(EMailAddress emailAdress)
		{
			super.emailAdress(emailAdress);
			return this;
		}

		@Override
		public AuthorizableOrgUnitBuilder superOuId(String superOuId)
		{
			super.superOuId(superOuId);
			return this;
		}
		
		// builder:
		@Override
		public AuthorizableOrgUnit build()
		{
			super.build();
			
			return new AuthorizableOrgUnit(this);
		}
	}
}
