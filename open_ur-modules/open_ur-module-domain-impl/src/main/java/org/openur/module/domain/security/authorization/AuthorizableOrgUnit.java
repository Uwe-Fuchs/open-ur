package org.openur.module.domain.security.authorization;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.openur.module.domain.userstructure.orgunit.AbstractOrgUnit;
import org.openur.module.domain.userstructure.orgunit.AbstractOrgUnitBuilder;
import org.openur.module.domain.userstructure.person.IPerson;

public class AuthorizableOrgUnit
	extends AbstractOrgUnit
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

	@Override
	public AuthorizableMember findMember(String id)
	{
		return (AuthorizableMember) IAuthorizableOrgUnit.super.findMember(id);
	}

	@Override
	public AuthorizableMember findMember(IPerson person)
	{
		return (AuthorizableMember) IAuthorizableOrgUnit.super.findMember(person);
	}

	@Override
	public AuthorizableOrgUnit getSuperOrgUnit()
	{
		return (AuthorizableOrgUnit) super.getSuperOrgUnit();
	}

	@Override
	public AuthorizableOrgUnit getRootOrgUnit()
	{
		return (AuthorizableOrgUnit) super.getRootOrgUnit();
	}

	// builder-class:
	public static class AuthorizableOrgUnitBuilder
		extends AbstractOrgUnitBuilder<AuthorizableOrgUnitBuilder>
	{
		// constructor:
		public AuthorizableOrgUnitBuilder(String orgUnitNumber, String name)
		{
			super(orgUnitNumber, name);
		}
		
		// builder-methods:
		public AuthorizableOrgUnitBuilder superOrgUnit(AuthorizableOrgUnit superOrgUnit)
		{
			super.superOrgUnit(superOrgUnit);
			
			return this;
		}

		public AuthorizableOrgUnitBuilder rootOrgUnit(AuthorizableOrgUnit rootOrgUnit)
		{
			super.rootOrgUnit(rootOrgUnit);
			
			return this;
		}

		public AuthorizableOrgUnitBuilder authorizableMembers(Collection<AuthorizableMember> members)
		{
			super.members(members);
			
			return this;
		}		

		public AuthorizableOrgUnitBuilder addMember(AuthorizableMember member)
		{
			super.addMember(member);
			
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
