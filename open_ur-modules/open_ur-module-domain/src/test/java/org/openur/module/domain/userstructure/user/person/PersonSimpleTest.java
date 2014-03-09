package org.openur.module.domain.userstructure.user.person;

import static org.junit.Assert.*;

import java.util.UUID;

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
		PersonSimple p1 = new PersonSimple(pb);
		
		assertEquals(p1.getNumber(), "123abc");
	}

	@Test
	public void testCompareTo()
	{
		PersonSimpleBuilder pb = new PersonSimpleBuilder(UUID.randomUUID().toString(), "username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		PersonSimple p1 = new PersonSimple(pb);
		
		pb = new PersonSimpleBuilder(UUID.randomUUID().toString(), "username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE);
		PersonSimple p2 = new PersonSimple(pb);
		
		assertTrue("different personal numbers", p1.compareTo(p2) < 0);
		
		pb = new PersonSimpleBuilder("username2", "password2");
		pb.number("123abc");
		pb.status(Status.INACTIVE);
		p2 = new PersonSimple(pb);
		assertTrue("same personal numbers, but different status", p1.compareTo(p2) < 0);
		
		pb = new PersonSimpleBuilder("username2", "password2");
		pb.number("123abc");
		pb.status(Status.ACTIVE);
		p2 = new PersonSimple(pb);
		assertTrue("same personal numbers, same status", p1.compareTo(p2) == 0);
	}
}
