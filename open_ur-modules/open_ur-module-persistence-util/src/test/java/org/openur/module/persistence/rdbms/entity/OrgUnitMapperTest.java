package org.openur.module.persistence.rdbms.entity;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit.AuthorizableOrgUnitBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
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
	
		AuthorizableMember m2 = new AuthorizableMemberBuilder(pers2, OU_ID).build();
		AuthorizableMember m1 = new AuthorizableMemberBuilder(pers1, OU_ID).build();		
		
		AuthorizableOrgUnit rootOu = new AuthorizableOrgUnitBuilder("rootOuNo", "rootOu")
			.build();
		
		AuthorizableOrgUnit superOu = new AuthorizableOrgUnitBuilder("superOuNo", "superOu")
			.rootOrgUnit(rootOu)
		  .superOrgUnit(rootOu)
			.build();
		
		Address address = new AddressBuilder("11")
			.country(Country.byCode("DE"))
			.city("city_1")
			.street("street_1")
			.poBox("poBox_1")
			.build();
		
		AuthorizableOrgUnit orgUnit = new AuthorizableOrgUnitBuilder("orgUnitNo", "staff department")
			.identifier(OU_ID)
			.status(Status.ACTIVE)
			.shortName("stf")
			.description("managing staff")
			.rootOrgUnit(rootOu)
			.superOrgUnit(superOu)
			.authorizableMembers(Arrays.asList(m1, m2))
			.address(address)
			.emailAddress(EMailAddress.create("staff@company.com"))
			.build();
		
		POrganizationalUnit pRootOu = OrganizationalUnitMapper.mapRootOuFromImmutable(rootOu);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(rootOu, pRootOu));
		
		POrganizationalUnit pSuperOu = OrganizationalUnitMapper.mapSuperOuFromImmutable(superOu, pRootOu);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(superOu, pSuperOu));
		
		POrganizationalUnit pOrgUnit = OrganizationalUnitMapper.mapFromImmutable(orgUnit, pRootOu, pSuperOu);		
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}

	@Test
	public void testMapFromEntity()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit();
		pRootOu.setOrgUnitNumber("rootOuNo");
		pRootOu.setName("rootOu");
		
		POrganizationalUnit pSuperOu = new POrganizationalUnit();
		pSuperOu.setOrgUnitNumber("superOuNo");
		pSuperOu.setName("superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);	
		
		POrganizationalUnit pOrgUnit = new POrganizationalUnit();
		pOrgUnit.setOrgUnitNumber("orgUnitNo");
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
		pPerson1.setEmployeeNumber("persNo1");
		pPerson1.setGender(Gender.MALE);
		pPerson1.setFirstName("Barack");
		pPerson1.setLastName("Obama");
		
		PPerson pPerson2 = new PPerson();
		pPerson2.setEmployeeNumber("persNo2");
		pPerson2.setGender(Gender.FEMALE);
		pPerson2.setTitle(Title.DR);
		pPerson2.setFirstName("Angela");
		pPerson2.setLastName("Merkel1");
		
		PRole pRole1 = new PRole();
		pRole1.setRoleName("role1");
		pRole1.setDescription("description role1");
		
		PRole pRole2 = new PRole();
		pRole2.setRoleName("role2");
		pRole2.setDescription("description role2");
		
		POrgUnitMember pMember1 = new POrgUnitMember(pOrgUnit, pPerson1);
		pMember1.addRole(pRole1);
		
		POrgUnitMember pMember2 = new POrgUnitMember(pOrgUnit, pPerson2);
		pMember2.addRole(pRole2);
		
		pOrgUnit.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember1, pMember2)));
		
		AuthorizableOrgUnit rootOu = OrganizationalUnitMapper.mapRootOuFromEntity(pRootOu);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(rootOu, pRootOu));
		
		AuthorizableOrgUnit superOu = OrganizationalUnitMapper.mapSuperOuFromEntity(pSuperOu, rootOu);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(superOu, pSuperOu));
		
		AuthorizableOrgUnit orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, rootOu, superOu);		
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}

	public static boolean immutableEqualsToEntity(AuthorizableOrgUnit immutable, POrganizationalUnit persistable)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityUserStructureBase(immutable, persistable))
		{
			return false;
		}
	
		if ((immutable.getAddress() != null || persistable.getAddress() != null)
			&& !AddressMapperTest.immutableEqualsToEntity(immutable.getAddress(),	persistable.getAddress()))
		{
			return false;
		}
	
		for (POrgUnitMember pMember : persistable.getMembers())
		{
			AuthorizableMember member = findMemberInImmutable(pMember, immutable);
	
			if (member == null || !OrgUnitMapperTest.immutableMemberEqualsToEntityMember(member, pMember))
			{
				return false;
			}
		}
		
		return new EqualsBuilder()
			.append(immutable.getOrgUnitNumber(), persistable.getOrgUnitNumber())
			.append(immutable.getName(), persistable.getName())
			.append(immutable.getShortName(), persistable.getShortName())
			.append(immutable.getDescription(), persistable.getDescription())
			.append(immutable.getEmailAddress() != null ? immutable.getEmailAddress()
					.getAsPlainEMailAddress() : null, persistable.getEmailAddress())
			.isEquals();
	}
	
	private static boolean immutableMemberEqualsToEntityMember(AuthorizableMember immutable, POrgUnitMember persistable)
	{
		if (!PersonMapperTest.immutableEqualsToEntity(immutable.getPerson(), persistable.getPerson()))
		{
			return false;
		}
		
		for (PRole pRole : persistable.getRoles())
		{
			OpenURRole role = findRoleInImmutableMember(pRole, immutable);
			
			if (role == null || !RoleMapperTest.immutableEqualsToEntity(role, pRole))
			{
				return false;
			}
		}
		
		return true;
	}

	private static AuthorizableMember findMemberInImmutable(POrgUnitMember pMember, AuthorizableOrgUnit immutable)
	{
		for (AuthorizableMember member : immutable.getMembers())
		{
			if (PersonMapperTest.immutableEqualsToEntity(member.getPerson(), pMember.getPerson()))
			{
				return member;
			}
		}
	
		return null;
	}
	
	private static OpenURRole findRoleInImmutableMember(PRole pRole, AuthorizableMember immutableMember)
	{
		for (OpenURRole role : immutableMember.getRoles())
		{
			if (RoleMapperTest.immutableEqualsToEntity(role, pRole))
			{
				return role;
			}
		}
		
		return null;
	}
}
