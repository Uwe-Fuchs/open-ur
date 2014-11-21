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
		Person pers1 = new PersonBuilder(Name.create(Gender.MALE, "Barack", "Obama"))
			.number("persNo1")
			.build();
		
		Person pers2 = new PersonBuilder(Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.number("persNo2")
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
	
		OrgUnitMember m2 = new OrgUnitMember(pers2, OU_ID);
		OrgUnitMember m1 = new OrgUnitMember(pers1, OU_ID);		
		
		OrganizationalUnit rootOu = new OrganizationalUnitBuilder()
			.number("rootOuNo")
			.build();
		
		OrganizationalUnit superOu = new OrganizationalUnitBuilder(rootOu)
			.number("superOuNo")
		  .superOrgUnit(rootOu)
			.build();
		
		Address address = new AddressBuilder()
			.country(Country.byCode("DE"))
			.city("city_1")
			.postcode("11")
			.street("street_1")
			.poBox("poBox_1")
			.build();
		
		OrganizationalUnit orgUnit = new OrganizationalUnitBuilder(OU_ID, rootOu)
			.number("orgUnitNo")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf")
			.description("managing staff")
			.superOrgUnit(superOu)
			.orgUnitMembers(Arrays.asList(m1, m2))
			.address(address)
			.emailAddress(EMailAddress.create("staff@company.com"))
			.build();
		
		POrganizationalUnit pRootOu = OrganizationalUnitMapper.mapFromImmutable(rootOu, null, null);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(rootOu, pRootOu));
		
		POrganizationalUnit pSuperOu = OrganizationalUnitMapper.mapFromImmutable(superOu, pRootOu, null);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(superOu, pSuperOu));
		
		POrganizationalUnit pOrgUnit = OrganizationalUnitMapper.mapFromImmutable(orgUnit, pRootOu, pSuperOu);		
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}

	@Test
	public void testMapFromEntity()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit();
		pRootOu.setNumber("rootOuNo");
		
		POrganizationalUnit pSuperOu = new POrganizationalUnit();
		pSuperOu.setNumber("superOuNo");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);	
		
		POrganizationalUnit pOrgUnit = new POrganizationalUnit();
		pOrgUnit.setNumber("orgUnitno");
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
		
		OrganizationalUnit rootOu = OrganizationalUnitMapper.mapFromEntity(pRootOu, null, null);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(rootOu, pRootOu));
		
		OrganizationalUnit superOu = OrganizationalUnitMapper.mapFromEntity(pSuperOu, rootOu, null);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(superOu, pSuperOu));
		
		OrganizationalUnit orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, rootOu, superOu);		
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}
}
