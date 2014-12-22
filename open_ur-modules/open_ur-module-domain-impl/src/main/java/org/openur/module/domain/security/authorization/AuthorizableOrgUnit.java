package org.openur.module.domain.security.authorization;

import java.util.Set;
import java.util.stream.Collectors;

import org.openur.module.domain.userstructure.orgunit.AbstractExtendedOrgUnit;
import org.openur.module.domain.userstructure.orgunit.AbstractExtendedOrgUnitBuilder;

public class AuthorizableOrgUnit
	extends AbstractExtendedOrgUnit
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = 4157616065471955134L;

	// constructor:
	private AuthorizableOrgUnit(AuthorizableOrgUnitBuilder b)
	{
		super(b);
	}

	// accessors:
	@Override
	public Set<AuthorizableMember> getMembers()
	{
		return super.getMembers()
			.stream()
			.map(member -> (AuthorizableMember) member)
			.collect(Collectors.toSet()); 
	}

	// builder-class:
	public static class AuthorizableOrgUnitBuilder
		extends AbstractExtendedOrgUnitBuilder<AuthorizableOrgUnitBuilder>
	{
		// constructors:
		public AuthorizableOrgUnitBuilder(String orgUnitNumber, String name)
		{
			super(orgUnitNumber, name);
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
