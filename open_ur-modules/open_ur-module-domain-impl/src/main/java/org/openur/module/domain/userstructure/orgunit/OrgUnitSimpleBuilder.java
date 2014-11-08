package org.openur.module.domain.userstructure.orgunit;

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

	@Override
	public OrgUnitSimple build()
	{
		super.build();
		
		return new OrgUnitSimple(this);
	}
}
