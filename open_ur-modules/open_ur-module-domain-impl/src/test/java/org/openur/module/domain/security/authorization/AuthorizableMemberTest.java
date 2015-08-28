package org.openur.module.domain.security.authorization;

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
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;

public class AuthorizableMemberTest
{
	@Test
	public void testHasRole()
	{
		OpenURRole role1 = new OpenURRoleBuilder("role1")
			.build();		
		Person person = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.status(Status.ACTIVE)
			.build();
		final String OU_ID_1 = UUID.randomUUID().toString();
		AuthorizableMember member = new AuthorizableMemberBuilder(person, OU_ID_1)
			.addRole(role1)
			.creationDate(LocalDateTime.of(2012, Month.APRIL, 05, 11, 30))
			.build();		
		assertTrue(member.hasRole(role1));
		
		OpenURRole role2 = new OpenURRoleBuilder("role2")
			.build();		
		assertFalse(member.hasRole(role2));
		
		member = new AuthorizableMemberBuilder(person, OU_ID_1)
			.roles(Arrays.asList(role1, role2))
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
		OpenURRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm11, perm12)))
			.build();
		
		Person person = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.addApplication(app1)
			.build();		
		final String OU_ID_1 = UUID.randomUUID().toString();
		
		AuthorizableMember member = new AuthorizableMemberBuilder(person, OU_ID_1)
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
		OpenURRole role2 = new OpenURRoleBuilder("role2")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm21, perm22)))
			.build();
		
		assertFalse(member.hasPermission(perm21));
		assertFalse(member.hasPermission(perm22));
		
		member = new AuthorizableMemberBuilder(person, OU_ID_1)
			.addRole(role2)
			.build();
		
		assertFalse("member should still not have permission because user is not registrated for app2!", 
			member.hasPermission(perm21));
		assertFalse("member should still not have permission because user is not registrated for app2!", 
			member.hasPermission(perm22));
		
		person = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.addApplication(app2)
			.build();		
		member = new AuthorizableMemberBuilder(person, OU_ID_1)
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
		AuthorizableMember member = new AuthorizableMemberBuilder(person, "someOuId")
			.build();	
		member.hasPermission(null);
	}
}
