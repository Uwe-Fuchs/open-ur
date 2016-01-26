package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;
import org.openur.module.domain.userstructure.orgunit.AbstractOrgUnitMember.AbstractOrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.util.data.Status;

public class OrgUnitMemberTest
{
	@Test
	public void testCompareTo()
	{		
		final String OU_ID_1 = "XYZ";
		
		Person pers1 = new PersonBuilder("numberPers1", Name.create(null, null, "Meier"))
			.build();
		MyOrgUnitMember m11 = new MyOrgUnitMemberBuilder(pers1, OU_ID_1)
			.creationDate(LocalDateTime.of(2012, Month.APRIL, 05, 11, 30))
			.build();
		
		assertTrue(m11.compareTo(m11) == 0);
		
		Person pers2 = new PersonBuilder("numberPers2", Name.create(null, null, "Schulze"))
			.build();
		MyOrgUnitMember m21 = new MyOrgUnitMemberBuilder(pers2, OU_ID_1)
			.build();
		
		assertTrue("m11 should be before m21 because of names", m11.compareTo(m21) < 0);
		
		pers2 = new PersonBuilder("numberPers2", Name.create(null, null, "Meier"))
			.status(Status.INACTIVE)
			.build();
		m21 = new MyOrgUnitMemberBuilder(pers2, OU_ID_1)
			.build();
		
		assertTrue("m11 should be before m21 because of status", m11.compareTo(m21) < 0);
		
		final String OU_ID_2 = "ABC";
		MyOrgUnitMember m12 = new MyOrgUnitMemberBuilder(pers1, OU_ID_2).build();
		MyOrgUnitMember m22 = new MyOrgUnitMemberBuilder(pers2, OU_ID_2).build();
	
		assertTrue("m11 should be after m12 because of org-unit-id's", m11.compareTo(m12) > 0);
		assertTrue("m11 should be after m22 because of org-unit-id's", m11.compareTo(m22) > 0);
		assertTrue("m12 should be before m21 because of org-unit-id's", m12.compareTo(m21) < 0);
		assertTrue("m12 should be before m22 because of org-unit-id's", m12.compareTo(m22) < 0);
		assertTrue("m21 should be after m22 because of org-unit-id's", m21.compareTo(m22) > 0);
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// internal implementation of AbstractOrgUnitMember plus builder-class:
	static class MyOrgUnitMember
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
	}
	
	static class MyOrgUnitMemberBuilder
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
