package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;

public class OrganizationalUnitTest
{
	@Test
	public void testCompareTo()
	{
		OrganizationalUnitBuilder oub = new OrganizationalUnitBuilder()
			.number("123abc")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf");
		OrganizationalUnit ou = oub.build();
		
		oub = new OrganizationalUnitBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("accounts department")
			.shortName("acc");
		OrganizationalUnit ou2 = oub.build();
		
		assertTrue("ou1 should be after ou2 because of name", ou.compareTo(ou2) > 0);
		
		oub = new OrganizationalUnitBuilder()
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf2");
		ou2 = oub.build();
		
		assertTrue("ou1 should be before ou2 because of short-name", ou.compareTo(ou2) < 0);
		
		oub = new OrganizationalUnitBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf");
		ou2 = oub.build();
		
		assertTrue("ou1 should be before ou2 because of org-unit-number", ou.compareTo(ou2) < 0);
	
		oub = new OrganizationalUnitBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE);
		ou = oub.build();
	
		OrgUnitSimpleBuilder soub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE);
		OrgUnitSimple sou = soub.build();
	
		assertTrue("ou should be after ou-simple because of org-unit-number", ou.compareTo(sou) > 0);
	}
}
