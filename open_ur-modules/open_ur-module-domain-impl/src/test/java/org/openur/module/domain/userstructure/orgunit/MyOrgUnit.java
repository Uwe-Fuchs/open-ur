package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;
import java.util.stream.Collectors;

import org.openur.module.domain.userstructure.person.IPerson;


public class MyOrgUnit
	extends AbstractOrgUnit
{
	private static final long serialVersionUID = -1856092517371886423L;

	// constructor:
	protected MyOrgUnit(MyOrgUnitBuilder b)
	{
		super(b);
	}
	
	// accessors:
	@Override
	public MyOrgUnit getSuperOrgUnit()
	{
		return (MyOrgUnit) super.getSuperOrgUnit();
	}
	
	@Override
	public Set<MyOrgUnitMember> getMembers()
	{
		return super.getMembers()
			.stream()
			.map(member -> (MyOrgUnitMember) member)
			.collect(Collectors.toSet()); 
	}

	@Override
	public MyOrgUnit getRootOrgUnit()
	{
		return (MyOrgUnit) super.getRootOrgUnit();
	}
	
	@Override
	public MyOrgUnitMember findMember(String id)
	{
		if (id == null)
    {
      return null;
    }

    for (MyOrgUnitMember m : this.getMembers())
    {
      if (id.equals(m.getPerson().getIdentifier()))
      {
        return m;
      }
    }

    return null;
	}

	@Override
	public MyOrgUnitMember findMember(IPerson person)
	{
		if (person == null)
    {
      return null;
    }

    return findMember(person.getIdentifier());
	}
}
