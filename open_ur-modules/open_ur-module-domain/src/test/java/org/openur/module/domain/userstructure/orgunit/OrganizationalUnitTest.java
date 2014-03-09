package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.userstructure.Status;

public class OrganizationalUnitTest
{
	@Before
	public void setUp()
		throws Exception
	{
	}

	@Test
	public void testCompareTo()
	{
		OrganizationalUnitBuilder oub = new OrganizationalUnitBuilder()
			.number("123abc")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf");
		OrganizationalUnit ou = new OrganizationalUnit(oub);
		
		oub = new OrganizationalUnitBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("accounts department")
			.shortName("acc");
		OrganizationalUnit ou2 = new OrganizationalUnit(oub);
		
		assertTrue("ou1 should be after ou2 because of name", ou.compareTo(ou2) > 0);
		
		oub = new OrganizationalUnitBuilder()
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf2");
		ou2 = new OrganizationalUnit(oub);
		
		assertTrue("ou1 should be before ou2 because of short-name", ou.compareTo(ou2) < 0);
		
		oub = new OrganizationalUnitBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf");
		ou2 = new OrganizationalUnit(oub);
		
		assertTrue("ou1 should be before ou2 because of org-unit-number", ou.compareTo(ou2) < 0);
	}
}
