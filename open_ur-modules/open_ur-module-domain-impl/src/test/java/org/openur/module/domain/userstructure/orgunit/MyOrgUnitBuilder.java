package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;


public class MyOrgUnitBuilder
	extends AbstractOrgUnitBuilder<MyOrgUnitBuilder>
{
	// constructor:
	public MyOrgUnitBuilder(String orgUnitNumber, String name)
	{
		super(orgUnitNumber, name);
	}
	
	// builder-methods:
	public MyOrgUnitBuilder superOrgUnit(MyOrgUnit superOrgUnit)
	{
		super.superOrgUnit(superOrgUnit);
		
		return this;
	}

	public MyOrgUnitBuilder rootOrgUnit(MyOrgUnit rootOrgUnit)
	{
		super.rootOrgUnit(rootOrgUnit);
		
		return this;
	}

	public MyOrgUnitBuilder myOrgUnitMembers(Collection<MyOrgUnitMember> members)
	{
		super.members(members);
		
		return this;
	}	

	// builder:
	@Override
	public MyOrgUnit build()
	{
		super.build();
		
		return new MyOrgUnit(this);
	}
}
