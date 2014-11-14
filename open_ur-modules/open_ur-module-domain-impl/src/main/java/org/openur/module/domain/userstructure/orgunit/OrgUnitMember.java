package org.openur.module.domain.userstructure.orgunit;

import java.time.LocalDateTime;

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
	
	public OrgUnitMember(Person person, String orgUnitId, LocalDateTime creationDate)
	{
		super(person, orgUnitId, creationDate);
	}

	// accessors:
	@Override
	public Person getPerson()
	{
		return (Person) super.getPerson();
	}
}
