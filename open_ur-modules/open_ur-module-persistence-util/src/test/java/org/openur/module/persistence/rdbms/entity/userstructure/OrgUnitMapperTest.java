package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember.OrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;

public class OrgUnitMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		Person pers1 = new PersonBuilder("persNo1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		
		Person pers2 = new PersonBuilder("persNo2", Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
	
		OrgUnitMember m2 = new OrgUnitMemberBuilder(pers2, OU_ID).build();
		OrgUnitMember m1 = new OrgUnitMemberBuilder(pers1, OU_ID).build();		
		
		OrganizationalUnit rootOu = new OrganizationalUnitBuilder("rootOuNo", "rootOu")
			.build();
		
		OrganizationalUnit superOu = new OrganizationalUnitBuilder("superOuNo", "superOu")
			.rootOrgUnit(rootOu)
		  .superOrgUnit(rootOu)
			.build();
		
		Address address = new AddressBuilder("11")
			.country(Country.byCode("DE"))
			.city("city_1")
			.street("street_1")
			.poBox("poBox_1")
			.build();
		
		OrganizationalUnit orgUnit = new OrganizationalUnitBuilder("orgUnitNo", "staff department")
			.identifier(OU_ID)
			.status(Status.ACTIVE)
			.shortName("stf")
			.description("managing staff")
			.rootOrgUnit(rootOu)
			.superOrgUnit(superOu)
			.orgUnitMembers(Arrays.asList(m1, m2))
			.address(address)
			.emailAddress(EMailAddress.create("staff@company.com"))
			.build();
		
		POrganizationalUnit pRootOu = OrganizationalUnitMapper.mapRootOuFromImmutable(rootOu);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(rootOu, pRootOu));
		
		POrganizationalUnit pSuperOu = OrganizationalUnitMapper.mapSuperOuFromImmutable(superOu, pRootOu);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(superOu, pSuperOu));
		
		POrganizationalUnit pOrgUnit = OrganizationalUnitMapper.mapFromImmutable(orgUnit, pRootOu, pSuperOu);		
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}

	@Test
	public void testMapFromEntity()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit();
		pRootOu.setNumber("rootOuNo");
		pRootOu.setName("rootOu");
		
		POrganizationalUnit pSuperOu = new POrganizationalUnit();
		pSuperOu.setNumber("superOuNo");
		pSuperOu.setName("superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);	
		
		POrganizationalUnit pOrgUnit = new POrganizationalUnit();
		pOrgUnit.setNumber("orgUnitNo");
		pOrgUnit.setSuperOu(pSuperOu);
		pOrgUnit.setRootOu(pRootOu);
		pOrgUnit.setName("staff department");
		pOrgUnit.setShortName("stf");
		pOrgUnit.setDescription("managing staff");
		pOrgUnit.setEmailAddress("staff@company.com");

		PAddress pAddress = new PAddress();
		pAddress.setCountryCode("DE");
		pAddress.setCity("city_1");
		pAddress.setPostcode("11");
		pAddress.setStreet("street_1");
		pAddress.setPoBox("poBox_1");
		pOrgUnit.setAddress(pAddress);
		
		PPerson pPerson1 = new PPerson();
		pPerson1.setNumber("persNo1");
		pPerson1.setGender(Gender.MALE);
		pPerson1.setFirstName("Barack");
		pPerson1.setLastName("Obama");
		
		PPerson pPerson2 = new PPerson();
		pPerson2.setNumber("persNo2");
		pPerson2.setGender(Gender.FEMALE);
		pPerson2.setTitle(Title.DR);
		pPerson2.setFirstName("Angela");
		pPerson2.setLastName("Merkel1");
		
		POrgUnitMember pMember1 = new POrgUnitMember(pOrgUnit, pPerson1);
		POrgUnitMember pMember2 = new POrgUnitMember(pOrgUnit, pPerson2);
		pOrgUnit.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember1, pMember2)));
		
		OrganizationalUnit rootOu = OrganizationalUnitMapper.mapRootOuFromEntity(pRootOu);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(rootOu, pRootOu));
		
		OrganizationalUnit superOu = OrganizationalUnitMapper.mapSuperOuFromEntity(pSuperOu, rootOu);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(superOu, pSuperOu));
		
		OrganizationalUnit orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, rootOu, superOu);		
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}
}
