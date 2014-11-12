package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;
import java.util.stream.Collectors;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;
import org.openur.module.domain.userstructure.person.IPerson;

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
	public OrgUnitSimple getSuperOrgUnit()
	{
		return (OrgUnitSimple) super.superOrgUnit;
	}

	@Override
	public Set<OrgUnitMemberSimple> getMembers()
	{
		return super.getMembers()
			.stream()
			.map(member -> (OrgUnitMemberSimple) member)
			.collect(Collectors.toSet());
	}

	@Override
	public OrgUnitMemberSimple findMember(String id)
	{
		return (OrgUnitMemberSimple) super.findMember(id);
	}

	@Override
	public OrgUnitMemberSimple findMember(IPerson person)
	{
		return (OrgUnitMemberSimple) super.findMember(person);
	}
}
