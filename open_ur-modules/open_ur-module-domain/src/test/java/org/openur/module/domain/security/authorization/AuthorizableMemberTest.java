package org.openur.module.domain.security.authorization;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.PersonSimpleBuilder;

public class AuthorizableMemberTest
{
	@Test
	public void testHasRole()
	{
		IRole role1 = new OpenURRoleBuilder("role1")
			.build();		
		IPerson pers = new PersonSimpleBuilder("username1", "password1")
			.number("123")
			.status(Status.ACTIVE)
			.build();
		final String OU_ID_1 = UUID.randomUUID().toString();
		IAuthorizableMember member = new AuthorizableMember(pers, OU_ID_1, Arrays.asList(role1));		
		assertTrue(member.hasRole(role1));
		
		IRole role2 = new OpenURRoleBuilder("role2")
			.build();		
		assertFalse(member.hasRole(role2));
		
		member = new AuthorizableMember(pers, OU_ID_1, Arrays.asList(role1, role2));
		assertTrue(member.hasRole(role1));
		assertTrue(member.hasRole(role2));
	}

	@Test
	public void testHasPermission()
	{
		IApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		IPermission perm11 = new OpenURPermissionBuilder("perm11", PermissionScope.SELECTED,  app1)
			.build();
		IPermission perm12 = new OpenURPermissionBuilder("perm12", PermissionScope.SELECTED_SUB,  app1)
			.build();		
		IRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm11, perm12)))
			.build();
		
		IPerson person = new PersonSimpleBuilder("username1", "password1")
			.build();		
		final String OU_ID_1 = UUID.randomUUID().toString();
		
		IAuthorizableMember member = new AuthorizableMember(person, OU_ID_1, Arrays.asList(role1));		
		
		assertTrue(member.hasPermission(app1, perm11));
		assertTrue(member.hasPermission(app1, perm12));
		
		IApplication app2 = new OpenURApplicationBuilder("app2")
			.build();		
		IPermission perm21 = new OpenURPermissionBuilder("perm21", PermissionScope.SELECTED,  app2)
			.build();
		IPermission perm22 = new OpenURPermissionBuilder("perm22", PermissionScope.SUB,  app2)
			.build();		
		IRole role2 = new OpenURRoleBuilder("role2")
		.permissions(new HashSet<IPermission>(Arrays.asList(perm21, perm22)))
		.build();
		
		assertFalse(member.hasPermission(app2, perm22));
		assertFalse(member.hasPermission(app2, perm22));
		
		member = new AuthorizableMember(person, OU_ID_1, Arrays.asList(role1, role2));
		assertTrue(member.hasPermission(app2, perm22));
		assertTrue(member.hasPermission(app2, perm22));
	}
}
