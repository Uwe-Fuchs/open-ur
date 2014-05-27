package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.person.PersonBuilder;

public class OrgUnitMemberTest
{
	@Test
	public void testEqualsObject()
	{
		IPerson pers1 = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE)
			.build();
		
		final String OU_ID_1 = UUID.randomUUID().toString();
		IOrgUnitMember m11 = new OrgUnitMember(pers1, OU_ID_1);
		
		assertTrue(m11.equals(m11));
		
		IPerson pers2 = new PersonBuilder("username2", "password2")
			.build();
		IOrgUnitMember m21 = new OrgUnitMember(pers2, OU_ID_1);	
		
		assertFalse(m11.equals(m21));
		assertFalse(m21.equals(m11));

		final String OU_ID_2 = UUID.randomUUID().toString();
		IOrgUnitMember m12 = new OrgUnitMember(pers1, OU_ID_2);
		IOrgUnitMember m22 = new OrgUnitMember(pers2, OU_ID_2);
		
		assertFalse(m11.equals(m12));
		assertFalse(m21.equals(m12));
		assertFalse(m22.equals(m12));
		assertFalse(m11.equals(m22));
		assertFalse(m12.equals(m22));
		assertFalse(m21.equals(m22));
	}

	@Test
	public void testCompareTo()
	{		
		IPerson pers1 = new PersonBuilder("username1", "password1")
			.number("123")
			.status(Status.ACTIVE)
			.build();
		final String OU_ID_1 = "XYZ";
		IOrgUnitMember m11 = new OrgUnitMember(pers1, OU_ID_1);
		
		assertTrue(m11.compareTo(m11) == 0);
		
		IPerson pers2 = new PersonBuilder("username2", "password2")
			.number("456")
			.status(Status.ACTIVE)
			.build();
		IOrgUnitMember m21 = new OrgUnitMember(pers2, OU_ID_1);
		
		assertTrue(m11.compareTo(m21) < 0);
		
		final String OU_ID_2 = "ABC";
		IOrgUnitMember m12 = new OrgUnitMember(pers1, OU_ID_2);
		IOrgUnitMember m22 = new OrgUnitMember(pers2, OU_ID_2);
	
		assertTrue(m11.compareTo(m12) > 0);
		assertTrue(m11.compareTo(m22) > 0);
		assertTrue(m12.compareTo(m21) < 0);
		assertTrue(m12.compareTo(m22) < 0);
		assertTrue(m21.compareTo(m22) > 0);
	}
}
