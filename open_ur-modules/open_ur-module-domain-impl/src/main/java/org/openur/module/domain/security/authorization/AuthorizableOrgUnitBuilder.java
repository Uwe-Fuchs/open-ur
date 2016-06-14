package org.openur.module.domain.security.authorization;

import java.util.Collection;

import org.openur.module.domain.userstructure.orgunit.AbstractOrgUnitBuilder;

// builder-class:
public class AuthorizableOrgUnitBuilder
	extends AbstractOrgUnitBuilder<AuthorizableOrgUnitBuilder>
{
	// constructors:
	public AuthorizableOrgUnitBuilder(String orgUnitNumber, String name)
	{
		super(orgUnitNumber, name);
	}
	
	public AuthorizableOrgUnitBuilder()
	{
		super();
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