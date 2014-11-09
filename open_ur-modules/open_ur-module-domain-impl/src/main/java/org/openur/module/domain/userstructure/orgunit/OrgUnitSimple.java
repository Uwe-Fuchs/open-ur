package org.openur.module.domain.userstructure.orgunit;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;

public class OrgUnitSimple
	extends AbstractOrgUnit
{
	private static final long serialVersionUID = 2563758496079145215L;
  
	// constructor:
	protected OrgUnitSimple(OrgUnitSimpleBuilder b)
	{
		super(b);
	}

	// accessors:
	@Override
	public OrgUnitSimple getRootOrgUnit()
	{
		return (OrgUnitSimple) super.rootOrgUnit;
	}

	@Override
	public IOrganizationalUnit getSuperOu()
	{
		return (OrgUnitSimple) super.superOrgUnit;
	}
}
