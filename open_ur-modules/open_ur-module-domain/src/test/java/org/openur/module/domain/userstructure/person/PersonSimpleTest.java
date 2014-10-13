package org.openur.module.domain.userstructure.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;

public class PersonSimpleTest
{
	@Test
	public void testCreate()
	{
		PersonSimpleBuilder pb = new PersonSimpleBuilder()
			.number("123abc")
			.status(Status.INACTIVE);		
		PersonSimple p1 = pb.build();
		
		assertEquals(p1.getNumber(), "123abc");
	}

	@Test
	public void testCompareTo()
	{
		PersonSimpleBuilder pb = new PersonSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE);
		PersonSimple p1 = pb.build();
		
		pb = new PersonSimpleBuilder()
			.number("456xyz")
			.status(Status.ACTIVE);
		PersonSimple p2 = pb.build();
		
		assertTrue("different personal numbers", p1.compareTo(p2) < 0);
		
		pb = new PersonSimpleBuilder();
		pb.number("123abc");
		pb.status(Status.INACTIVE);
		p2 = pb.build();
		assertTrue("same personal numbers, but different status", p1.compareTo(p2) < 0);
		
		pb = new PersonSimpleBuilder();
		pb.number("123abc");
		pb.status(Status.ACTIVE);
		p2 = pb.build();
		assertTrue("same personal numbers, same status", p1.compareTo(p2) == 0);
	}
}
