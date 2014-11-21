package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;

public class OrgUnitMapperTest
{
//	@Test
//	public void testMapFromImmutable()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMapFromEntity()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testImmutableEqualsToEntity()
//	{
//		fail("Not yet implemented");
//	}

	@Test
	public void testImmutableMemberEqualsToEntityMember()
	{
		final String OU_ID = UUID.randomUUID().toString();
		
		Person pers1 = new PersonBuilder(Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		
		Person pers2 = new PersonBuilder(Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();

		OrgUnitMember m1 = new OrgUnitMember(pers1, OU_ID);
		OrgUnitMember m2 = new OrgUnitMember(pers2, OU_ID);
		
		OrganizationalUnit orgUnit = new OrganizationalUnitBuilder(OU_ID)
			.build();
		
		POrganizationalUnit pOrgUnit = OrganizationalUnitMapper.mapFromImmutable(orgUnit, null, null);
		PPerson pPers1 = PersonMapper.mapFromImmutable(pers1);
		PPerson pPers2 = PersonMapper.mapFromImmutable(pers2);
		
		POrgUnitMember pMember1 = new POrgUnitMember(pOrgUnit, pPers1);
		POrgUnitMember pMember2 = new POrgUnitMember(pOrgUnit, pPers2);
		
		assertTrue(OrganizationalUnitMapper.immutableMemberEqualsToEntityMember(m1, pMember1));
		assertTrue(OrganizationalUnitMapper.immutableMemberEqualsToEntityMember(m2, pMember2));
	}
}
