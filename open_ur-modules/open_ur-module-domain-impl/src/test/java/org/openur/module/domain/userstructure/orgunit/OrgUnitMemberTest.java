package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember.OrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.util.data.Gender;
import org.openur.module.util.data.Status;

public class OrgUnitMemberTest
{
	@Test
	public void testCompareTo()
	{		
		final String OU_ID_1 = "XYZ";
		
		Person pers1 = new PersonBuilder("numberPers1", Name.create(null, null, "Meier"))
			.build();
		OrgUnitMember m11 = new OrgUnitMemberBuilder(pers1, OU_ID_1)
			.creationDate(LocalDateTime.of(2012, Month.APRIL, 05, 11, 30))
			.build();
		
		assertTrue(m11.compareTo(m11) == 0);
		
		Person pers2 = new PersonBuilder("numberPers2", Name.create(null, null, "Schulze"))
			.build();
		OrgUnitMember m21 = new OrgUnitMemberBuilder(pers2, OU_ID_1)
			.build();
		
		assertTrue("m11 should be before m21 because of names", m11.compareTo(m21) < 0);
		
		pers2 = new PersonBuilder("numberPers2", Name.create(null, null, "Meier"))
			.status(Status.INACTIVE)
			.build();
		m21 = new OrgUnitMemberBuilder(pers2, OU_ID_1)
			.build();
		
		assertTrue("m11 should be before m21 because of status", m11.compareTo(m21) < 0);
		
		final String OU_ID_2 = "ABC";
		OrgUnitMember m12 = new OrgUnitMemberBuilder(pers1, OU_ID_2).build();
		OrgUnitMember m22 = new OrgUnitMemberBuilder(pers2, OU_ID_2).build();
	
		assertTrue("m11 should be after m12 because of org-unit-id's", m11.compareTo(m12) > 0);
		assertTrue("m11 should be after m22 because of org-unit-id's", m11.compareTo(m22) > 0);
		assertTrue("m12 should be before m21 because of org-unit-id's", m12.compareTo(m21) < 0);
		assertTrue("m12 should be before m22 because of org-unit-id's", m12.compareTo(m22) < 0);
		assertTrue("m21 should be after m22 because of org-unit-id's", m21.compareTo(m22) > 0);
	}
	
	@Test
	public void testHasRole()
	{
		OpenURRole role1 = new OpenURRoleBuilder("role1")
			.build();		
		Person person = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.status(Status.ACTIVE)
			.build();
		final String OU_ID_1 = UUID.randomUUID().toString();
		OrgUnitMember member = new OrgUnitMemberBuilder(person, OU_ID_1)
			.addRole(role1)
			.creationDate(LocalDateTime.of(2012, Month.APRIL, 05, 11, 30))
			.build();		
		assertTrue(member.hasRole(role1));
		
		OpenURRole role2 = new OpenURRoleBuilder("role2")
			.build();		
		assertFalse(member.hasRole(role2));
		
		member = new OrgUnitMemberBuilder(person, OU_ID_1)
			.roles(Arrays.asList(role1, role2))
			.identifier("someId")
			.build();
		assertTrue(member.hasRole(role1));
		assertTrue(member.hasRole(role2));
	}

	@Test
	public void testHasPermission()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm11 = new OpenURPermissionBuilder("perm11", app1)
			.build();
		OpenURPermission perm12 = new OpenURPermissionBuilder("perm12", app1)
			.build();	
		OpenURRoleBuilder roleBuilder = new OpenURRoleBuilder("role1");
		OpenURRole role1 = roleBuilder
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm11, perm12)), roleBuilder)
			.build();
		
		Person person = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.addApplication(app1)
			.build();		
		final String OU_ID_1 = UUID.randomUUID().toString();
		
		OrgUnitMember member = new OrgUnitMemberBuilder(person, OU_ID_1)
			.addRole(role1)
			.build();		
		assertTrue(member.hasPermission(perm11));
		assertTrue(member.hasPermission(perm12));
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();		
		OpenURPermission perm21 = new OpenURPermissionBuilder("perm21", app2)
			.build();
		OpenURPermission perm22 = new OpenURPermissionBuilder("perm22", app2)
			.build();
		roleBuilder = new OpenURRoleBuilder("role2");
		OpenURRole role2 = roleBuilder
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm21, perm22)), roleBuilder)
			.build();
		
		assertFalse(member.hasPermission(perm21));
		assertFalse(member.hasPermission(perm22));
		
		member = new OrgUnitMemberBuilder(person, OU_ID_1)
			.addRole(role2)
			.build();
		
		assertFalse("member should still not have permission because user is not registrated for app2!", 
			member.hasPermission(perm21));
		assertFalse("member should still not have permission because user is not registrated for app2!", 
			member.hasPermission(perm22));
		
		person = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.addApplication(app2)
			.build();		
		member = new OrgUnitMemberBuilder(person, OU_ID_1)
			.addRole(role2)
			.build();
		
		assertTrue(member.hasPermission(perm21));
		assertTrue(member.hasPermission(perm22));
	}

	@Test(expected=NullPointerException.class)
	public void testHasPermissionEmptyPermission()
	{
		Person person = new PersonBuilder("somePersonNo", Name.create(Gender.MALE, "someFirstName", "someLastName"))
			.build();				
		OrgUnitMember member = new OrgUnitMemberBuilder(person, "someOuId")
			.build();	
		member.hasPermission(null);
	}
}
