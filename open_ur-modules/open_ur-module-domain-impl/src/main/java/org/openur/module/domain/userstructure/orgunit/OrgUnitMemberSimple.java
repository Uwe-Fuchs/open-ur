package org.openur.module.domain.userstructure.orgunit;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitMember;
import org.openur.module.domain.userstructure.person.PersonSimple;

public class OrgUnitMemberSimple
	extends AbstractOrgUnitMember
	implements IOrgUnitMember
{	
	private static final long serialVersionUID = -7870674354648331339L;

	// constructor:
	public OrgUnitMemberSimple(PersonSimple person, String orgUnitId)
	{
		super(person, orgUnitId);
	}

	// accessors:
	@Override
	public PersonSimple getPerson()
	{
		return (PersonSimple) super.getPerson();
	}
}
