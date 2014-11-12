package org.openur.module.domain.security.authorization;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitBuilder;
import org.openur.module.domain.userstructure.person.IPerson;

public class AuthOrgUnitSimple
	extends AbstractOrgUnit
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = -9115069249614557685L;

	// constructor:
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
	public AuthOrgUnitSimple getSuperOrgUnit()
	{
		return (AuthOrgUnitSimple) super.superOrgUnit;
	}
	
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
		return (AuthorizableMember) super.findMember(id);
	}

	@Override
	public AuthorizableMember findMember(IPerson person)
	{
		return (AuthorizableMember) super.findMember(person);
	}

	// builder-class:
	public static class AuthOrgUnitSimpleBuilder extends AbstractOrgUnitBuilder<AuthOrgUnitSimpleBuilder>
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

		public AuthOrgUnitSimpleBuilder(AuthOrgUnitSimple rootOrgUnit)
		{
			super(rootOrgUnit);
		}

		public AuthOrgUnitSimpleBuilder(String identifier, 	AuthOrgUnitSimple rootOrgUnit)
		{
			super(identifier, rootOrgUnit);
		}

		// accessors:
		public AuthOrgUnitSimpleBuilder authorizableMembers(Collection<AuthorizableMember> authMembers)
		{
			super.members(authMembers);
			return this;
		}

		public AuthOrgUnitSimpleBuilder superOrgUnit(AuthOrgUnitSimple superOrgUnit)
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
