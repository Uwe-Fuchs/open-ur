package org.openur.module.service.security;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.security.OpenURApplicationBuilder;
import org.openur.module.domain.security.OpenURPermissionBuilder;
import org.openur.module.domain.security.OpenURRoleBuilder;
import org.openur.module.domain.security.PermissionScope;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrgUnitDelegateBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimpleBuilder;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.person.PersonBuilder;
import org.openur.module.persistence.security.ISecurityDao;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class SecurityAuthTest
{
	@Inject
	private ISecurityDao dao;
	
	@Inject
	private ISecurityAuthServices securityAuthServices;

	@Test
	public void testAuthenticate()
	{
		// Nothing to test yet => job is done completely by Shiro.
		// maybe test the integration of different security-clients like Shiro, Spring-Sec, JAAS ??
		assertTrue(true);
	}

	@Test
	public void testHasPermissionIPersonIOrganizationalUnitIPermissionIApplication()
	{
		IApplication app1 = new OpenURApplicationBuilder("app1", "user1", "pw1")
			.build();		
		IPermission perm11 = new OpenURPermissionBuilder("perm11", PermissionScope.SELECTED,  app1)
			.build();		
		IRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm11)))
			.build();
		
		IPerson person = new PersonBuilder("username1", "password1")
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();		
		IOrganizationalUnit oud = new OrgUnitDelegateBuilder(OU_ID)
			.build();
		
		IOrgUnitMember member = new OrgUnitMemberBuilder(person, oud)
		  .roles(Arrays.asList(role1))
		  .build();
		
		IOrganizationalUnit ou = new OrgUnitSimpleBuilder(OU_ID)
			.members(Arrays.asList(member))
			.build();
		
		// has permission in org-unit:
		assertTrue(securityAuthServices.hasPermission(person, ou, perm11, app1));
		
		// doesn't have permission:
		IPermission perm12 = new OpenURPermissionBuilder("perm12", PermissionScope.SELECTED_SUB,  app1)
			.build();
		
		assertTrue(securityAuthServices.hasPermission(person, ou, perm12, app1));
		
		// has permission in super-org-unit:
		
		
	}

//	@Test
//	public void testHasPermissionIPersonIPermissionIApplication()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testHasPermissionStringStringIApplication()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testHasPermissionStringStringStringIApplication()
//	{
//		fail("Not yet implemented");
//	}
}
