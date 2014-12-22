package org.openur.module.domain.userstructure.orgunit;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitMember;
import org.openur.module.domain.userstructure.person.Person;

public class MyOrgUnitMember
	extends AbstractOrgUnitMember
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -3347655382807063939L;

	// constructor:
	public MyOrgUnitMember(MyOrgUnitMemberBuilder b)
	{
		super(b);
	}

	// accessors:
	@Override
	public Person getPerson()
	{
		return (Person) super.getPerson();
	}
	
	// builder-class:
	public static class MyOrgUnitMemberBuilder
		extends AbstractOrgUnitMemberBuilder<MyOrgUnitMemberBuilder>
	{
		public MyOrgUnitMemberBuilder(Person person, String orgUnitId)
		{
			super(person, orgUnitId);
		}

		@Override
		public MyOrgUnitMember build()
		{
			return new MyOrgUnitMember(this);
		}
	}
}
