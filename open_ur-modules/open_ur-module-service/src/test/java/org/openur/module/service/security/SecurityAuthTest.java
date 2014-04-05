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
import org.openur.module.domain.userstructure.Status;
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
	public void testHasPermissionInOrgUnit()
	{
		IApplication app1 = new OpenURApplicationBuilder("app1", "user1", "pw1")
			.build();		
		IPermission perm11 = new OpenURPermissionBuilder("perm11", PermissionScope.SELECTED,  app1)
			.build();
		IPermission perm12 = new OpenURPermissionBuilder("perm12", PermissionScope.SELECTED_SUB,  app1)
			.build();
		
		OpenURRoleBuilder orb = new OpenURRoleBuilder("role1");
		
		IPerson person = new PersonBuilder("username1", "password1")
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		final String OU_NUMBER = "123abc";
		final Status OU_STATUS = Status.ACTIVE;
		
		IOrganizationalUnit oud = new OrgUnitDelegateBuilder(OU_ID)
			.number(OU_NUMBER)
			.status(OU_STATUS)
			.build();
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder(OU_ID)
			.number(OU_NUMBER)
			.status(OU_STATUS);
		
		// has permission in org-unit:	
		IRole role1 = orb
			.permissions(new HashSet<IPermission>(Arrays.asList(perm11, perm12)))
			.build();
		
		IOrgUnitMember member = new OrgUnitMemberBuilder(person, oud)
		  .roles(Arrays.asList(role1))
		  .build();
		
		IOrganizationalUnit ou = oub
			.members(Arrays.asList(member))
			.build();
		
		assertTrue(securityAuthServices.hasPermission(person, ou, app1, perm12));
		
		// has permission in super-org-unit:
		
		
		// doesn't have permission:
//		role1 = orb
//			.permissions(new HashSet<IPermission>(Arrays.asList(perm11)))
//			.build();
//		
//		member = new OrgUnitMemberBuilder(person, oud)
//		  .roles(Arrays.asList(role1))
//		  .build();
//		
//		ou = oub
//			.members(Arrays.asList(member))
//			.build();
//		
//		assertFalse(securityAuthServices.hasPermission(person, ou, app1, perm12));
	}

//  @Test
//  public void testHasPermission()
//  {
//	  fail("Not yet implemented");
//  }
}
