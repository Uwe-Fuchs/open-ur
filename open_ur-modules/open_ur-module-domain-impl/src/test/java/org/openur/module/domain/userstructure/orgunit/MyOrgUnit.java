package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;
import java.util.stream.Collectors;


public class MyOrgUnit
	extends AbstractExtendedOrgUnit
{
	private static final long serialVersionUID = -1856092517371886423L;

	// constructor:
	protected MyOrgUnit(MyOrgUnitBuilder b)
	{
		super(b);
	}
	
	// accessors:
	/**
	 * convenience:
	 */
	@Override
	public Set<MyOrgUnitMember> getMembers()
	{
		return super.getMembers()
			.stream()
			.map(member -> (MyOrgUnitMember) member)
			.collect(Collectors.toSet()); 
	}

	@Override
	public MyOrgUnit getSuperOrgUnit()
	{
		return (MyOrgUnit) super.getSuperOrgUnit();
	}

	@Override
	public MyOrgUnit getRootOrgUnit()
	{
		return (MyOrgUnit) super.getRootOrgUnit();
	}
}
