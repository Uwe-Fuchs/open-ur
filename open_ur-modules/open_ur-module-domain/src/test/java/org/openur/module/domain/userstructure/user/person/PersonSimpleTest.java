package org.openur.module.domain.userstructure.user.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;

public class PersonSimpleTest
{
	@Test
	public void testCreate()
	{
		PersonSimpleBuilder pb = new PersonSimpleBuilder("username2", "password2")
			.number("123abc")
			.status(Status.INACTIVE);		
		IPerson p1 = pb.build();
		
		assertEquals(p1.getNumber(), "123abc");
	}

	@Test
	public void testCompareTo()
	{
		PersonSimpleBuilder pb = new PersonSimpleBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		IPerson p1 = pb.build();
		
		pb = new PersonSimpleBuilder("username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE);
		IPerson p2 = pb.build();
		
		assertTrue("different personal numbers", p1.compareTo(p2) < 0);
		
		pb = new PersonSimpleBuilder("username2", "password2");
		pb.number("123abc");
		pb.status(Status.INACTIVE);
		p2 = pb.build();
		assertTrue("same personal numbers, but different status", p1.compareTo(p2) < 0);
		
		pb = new PersonSimpleBuilder("username2", "password2");
		pb.number("123abc");
		pb.status(Status.ACTIVE);
		p2 = pb.build();
		assertTrue("same personal numbers, same status", p1.compareTo(p2) == 0);
	}
}
