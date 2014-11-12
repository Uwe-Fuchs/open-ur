package org.openur.module.domain.userstructure.orgunit;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitMember;
import org.openur.module.domain.userstructure.person.Person;

public class OrgUnitMember
	extends AbstractOrgUnitMember
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -3347655382807063939L;

	// constructor:
	public OrgUnitMember(Person person, String orgUnitId)
	{
		super(person, orgUnitId);
	}

	// accessors:
	@Override
	public Person getPerson()
	{
		return (Person) super.getPerson();
	}
}
