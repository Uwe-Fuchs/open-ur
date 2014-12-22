package org.openur.module.domain.userstructure.orgunit;


public class MyOrgUnitBuilder
	extends AbstractExtendedOrgUnitBuilder<MyOrgUnitBuilder>
{
	// constructor:
	public MyOrgUnitBuilder(String orgUnitNumber, String name)
	{
		super(orgUnitNumber, name);
	}

	// builder:
	@Override
	public MyOrgUnit build()
	{
		super.build();
		
		return new MyOrgUnit(this);
	}
}
