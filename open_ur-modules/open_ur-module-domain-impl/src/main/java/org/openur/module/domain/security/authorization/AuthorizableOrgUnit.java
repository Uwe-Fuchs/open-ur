package org.openur.module.domain.security.authorization;

import java.util.Set;
import java.util.stream.Collectors;

import org.openur.module.domain.userstructure.orgunit.AbstractOrgUnit;
import org.openur.module.domain.userstructure.person.IPerson;

public class AuthorizableOrgUnit
	extends AbstractOrgUnit
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = 4157616065471955134L;

	// constructor:
	AuthorizableOrgUnit(AuthorizableOrgUnitBuilder b)
	{
		super(b);
	}

	// accessors:
	@Override
	public Set<AuthorizableMember> getMembers()
	{
		return super.getMembers()
			.stream()
			.map(member -> (AuthorizableMember) member)
			.collect(Collectors.toSet()); 
	}

	@Override
	public AuthorizableMember findMemberByPersonId(String id)
	{
		return (AuthorizableMember) IAuthorizableOrgUnit.super.findMemberByPersonId(id);
	}

	@Override
	public AuthorizableMember findMemberByPerson(IPerson person)
	{
		return (AuthorizableMember) IAuthorizableOrgUnit.super.findMemberByPerson(person);
	}

	@Override
	public AuthorizableOrgUnit getSuperOrgUnit()
	{
		return (AuthorizableOrgUnit) super.getSuperOrgUnit();
	}

	@Override
	public AuthorizableOrgUnit getRootOrgUnit()
	{
		return (AuthorizableOrgUnit) super.getRootOrgUnit();
	}
}
