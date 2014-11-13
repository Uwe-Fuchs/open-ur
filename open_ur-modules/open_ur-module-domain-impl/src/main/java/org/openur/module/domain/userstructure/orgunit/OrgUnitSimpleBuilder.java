package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitBuilder;


public class OrgUnitSimpleBuilder
	extends AbstractOrgUnitBuilder<OrgUnitSimpleBuilder>
{
	// constructors:
	public OrgUnitSimpleBuilder()
	{
		super();
	}
	
	public OrgUnitSimpleBuilder(String identifier)
	{
		super(identifier);
	}
	
	public OrgUnitSimpleBuilder(OrgUnitSimple rootOrgUnit)
	{
		super(rootOrgUnit);
	}

	public OrgUnitSimpleBuilder(String identifier, OrgUnitSimple rootOrgUnit)
	{
		super(identifier, rootOrgUnit);
	}
	
	// builder-methods:
	public OrgUnitSimpleBuilder superOrgUnit(OrgUnitSimple superOrgUnit)
	{
		super.superOrgUnit(superOrgUnit);
		return this;
	}	

	public OrgUnitSimpleBuilder orgUnitMembers(Collection<OrgUnitMemberSimple> orgUnitMembers)
	{
		super.members(orgUnitMembers);
		return this;
	}

	public OrgUnitSimpleBuilder addMember(OrgUnitMemberSimple member)
	{
		super.addMember(member);
		return this;
	}

	// builder:
	@Override
	public OrgUnitSimple build()
	{
		super.build();
		
		return new OrgUnitSimple(this);
	}
}
