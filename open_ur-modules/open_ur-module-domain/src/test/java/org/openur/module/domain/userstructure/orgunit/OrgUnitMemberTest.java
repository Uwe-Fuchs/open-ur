package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.PersonSimple;
import org.openur.module.domain.userstructure.person.PersonSimpleBuilder;

public class OrgUnitMemberTest
{
	@Test
	public void testEqualsObject()
	{
		PersonSimple pers1 = new PersonSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE)
			.build();
		
		final String OU_ID_1 = UUID.randomUUID().toString();
		OrgUnitMember m11 = new OrgUnitMember(pers1, OU_ID_1);
		
		assertTrue(m11.equals(m11));
		
		PersonSimple pers2 = new PersonSimpleBuilder()
			.build();
		OrgUnitMember m21 = new OrgUnitMember(pers2, OU_ID_1);	
		
		assertFalse(m11.equals(m21));
		assertFalse(m21.equals(m11));

		final String OU_ID_2 = UUID.randomUUID().toString();
		OrgUnitMember m12 = new OrgUnitMember(pers1, OU_ID_2);
		OrgUnitMember m22 = new OrgUnitMember(pers2, OU_ID_2);
		
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
		PersonSimple pers1 = new PersonSimpleBuilder()
			.number("123")
			.status(Status.ACTIVE)
			.build();
		final String OU_ID_1 = "XYZ";
		OrgUnitMember m11 = new OrgUnitMember(pers1, OU_ID_1);
		
		assertTrue(m11.compareTo(m11) == 0);
		
		PersonSimple pers2 = new PersonSimpleBuilder()
			.number("456")
			.status(Status.ACTIVE)
			.build();
		OrgUnitMember m21 = new OrgUnitMember(pers2, OU_ID_1);
		
		assertTrue(m11.compareTo(m21) < 0);
		
		final String OU_ID_2 = "ABC";
		OrgUnitMember m12 = new OrgUnitMember(pers1, OU_ID_2);
		OrgUnitMember m22 = new OrgUnitMember(pers2, OU_ID_2);
	
		assertTrue(m11.compareTo(m12) > 0);
		assertTrue(m11.compareTo(m22) > 0);
		assertTrue(m12.compareTo(m21) < 0);
		assertTrue(m12.compareTo(m22) < 0);
		assertTrue(m21.compareTo(m22) > 0);
	}
}
