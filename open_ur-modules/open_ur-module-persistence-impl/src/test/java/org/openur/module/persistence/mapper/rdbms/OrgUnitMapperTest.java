package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit.AuthorizableOrgUnitBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
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
import org.openur.module.persistence.rdbms.entity.PAddress;
import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PRole;

public class OrgUnitMapperTest
{
	@Test
	public void testMapFromImmutable()
	{		
		AuthorizableOrgUnit rootOu = new AuthorizableOrgUnitBuilder("rootOuNo", "rootOu")
			.build();
		
		POrganizationalUnit pRootOu = OrganizationalUnitMapper.mapRootOuFromImmutable(rootOu);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(rootOu, pRootOu));
		
		AuthorizableOrgUnit superOu = new AuthorizableOrgUnitBuilder("superOuNo", "superOu")
			.rootOrgUnit(rootOu)
		  .superOrgUnit(rootOu)
			.build();
		
		POrganizationalUnit pSuperOu = OrganizationalUnitMapper.mapSuperOuFromImmutable(superOu, pRootOu);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(superOu, pSuperOu));
		
		Person pers1 = new PersonBuilder("persNo1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();		
		Person pers2 = new PersonBuilder("persNo2", Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();
		
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1)
			.build();	
		OpenURRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1)))
			.build();
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.build();	
		OpenURRole role2 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm2)))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();

		AuthorizableMember m1 = new AuthorizableMemberBuilder(pers1, OU_ID)
			.roles(Arrays.asList(role1))
			.build();	
		AuthorizableMember m2 = new AuthorizableMemberBuilder(pers2, OU_ID)
			.roles(Arrays.asList(role2))
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
		
		POrganizationalUnit pOrgUnit = OrganizationalUnitMapper.mapFromImmutable(orgUnit);		
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}

	@Test
	public void testMapFromEntity()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit("rootOuNo", "rootOu");
		
		AuthorizableOrgUnit rootOu = OrganizationalUnitMapper.mapRootOuFromEntity(pRootOu);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(rootOu, pRootOu));
		
		POrganizationalUnit pSuperOu = new POrganizationalUnit("superOuNo", "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);	
		
		AuthorizableOrgUnit superOu = OrganizationalUnitMapper.mapSuperOuFromEntity(pSuperOu, rootOu);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(superOu, pSuperOu));
		
		POrganizationalUnit pOrgUnit = new POrganizationalUnit("orgUnitNo", "staff department");
		pOrgUnit.setSuperOu(pSuperOu);
		pOrgUnit.setRootOu(pRootOu);
		pOrgUnit.setShortName("stf");
		pOrgUnit.setDescription("managing staff");
		pOrgUnit.setEmailAddress("staff@company.com");

		PAddress pAddress = new PAddress("11");
		pAddress.setCountryCode("DE");
		pAddress.setCity("city_1");
		pAddress.setStreet("street_1");
		pAddress.setPoBox("poBox_1");
		pOrgUnit.setAddress(pAddress);
		
		PPerson pPerson1 = new PPerson("persNo1", "Obama");
		pPerson1.setGender(Gender.MALE);
		pPerson1.setFirstName("Barack");
		
		PPerson pPerson2 = new PPerson("persNo2", "Merkel");
		pPerson2.setGender(Gender.FEMALE);
		pPerson2.setTitle(Title.DR);
		pPerson2.setFirstName("Angela");
		
		PRole pRole1 = new PRole("role1");
		pRole1.setDescription("description role1");
		
		PRole pRole2 = new PRole("role2");
		pRole2.setDescription("description role2");
		
		POrgUnitMember pMember1 = new POrgUnitMember(pOrgUnit, pPerson1);
		pMember1.addRole(pRole1);
		
		POrgUnitMember pMember2 = new POrgUnitMember(pOrgUnit, pPerson2);
		pMember2.addRole(pRole2);
		
		pOrgUnit.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember1, pMember2)));
		
		AuthorizableOrgUnit orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, false, false);
		assertTrue(orgUnit.getMembers().isEmpty());
		
		orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, true, false);
		assertFalse(orgUnit.getMembers().isEmpty());
		for (AuthorizableMember member : orgUnit.getMembers())
		{
			assertTrue(member.getRoles().isEmpty());
		}
		
		orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, true, true);		
		assertFalse(orgUnit.getMembers().isEmpty());
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMapFromEntityWithoutMembersButWithRoles()
	{
		POrganizationalUnit pOrgUnit = new POrganizationalUnit("orgUnitNo", "staff department");
		OrganizationalUnitMapper.mapFromEntity(pOrgUnit, false, true);		
	}

	public static boolean immutableEqualsToEntity(AuthorizableOrgUnit immutable, POrganizationalUnit persistable)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityUserStructureBase(immutable, persistable))
		{
			return false;
		}
		
		if (immutable.getRootOrgUnit() != null || persistable.getRootOu() != null)
		{
			immutableEqualsToEntity(immutable.getRootOrgUnit(), persistable.getRootOu());
		}
		
		if (immutable.getSuperOrgUnit() != null || persistable.getSuperOu() != null)
		{
			immutableEqualsToEntity(immutable.getSuperOrgUnit(), persistable.getSuperOu());
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
			.append(immutable.getEmailAddress() != null ? immutable.getEmailAddress()	.getAsPlainEMailAddress() : null, persistable.getEmailAddress())
			.isEquals();
	}
	
	public static boolean immutableMemberEqualsToEntityMember(AuthorizableMember immutableMember, POrgUnitMember persistableMember)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityBase(immutableMember, persistableMember))
		{
			return false;
		}
		
		if (!PersonMapperTest.immutableEqualsToEntity(immutableMember.getPerson(), persistableMember.getPerson()))
		{
			return false;
		}
		
		for (PRole pRole : persistableMember.getRoles())
		{
			OpenURRole role = findRoleInImmutableMember(pRole, immutableMember);
			
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
