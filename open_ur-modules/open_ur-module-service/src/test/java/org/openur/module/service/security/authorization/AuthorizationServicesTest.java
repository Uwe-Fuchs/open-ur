package org.openur.module.service.security.authorization;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.AuthOrgUnitSimple;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimpleBuilder;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.module.service.security.SecurityTestSpringConfig;
import org.openur.module.service.security.authorization.IAuthorizationServices;
import org.openur.module.service.userstructure.user.IUserServices;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class AuthorizationServicesTest
{
	@Inject
	private IAuthorizationServices authorizationServices;
	
	@Inject
	private ISecurityDomainServices securityDomainServices;
	
	@Inject
	private IUserServices userServices;

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
		IPermission perm1 = new OpenURPermissionBuilder("perm1", PermissionScope.SELECTED, app1)
			.build();		
		IRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm1)))
			.build();
		
		IPerson person = new PersonBuilder("username1", "password1")
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		IAuthorizableMember member = new AuthorizableMember(person, OU_ID, Arrays.asList(role1));		
		IAuthorizableOrgUnit ou = new AuthorizableOrgUnit(
			new OrganizationalUnitBuilder(OU_ID), Arrays.asList(member));
		
		// has permission in org-unit:
		assertTrue(authorizationServices.hasPermission(person, ou, perm1, app1));
		
		// doesn't have permission:
		IPermission perm12 = new OpenURPermissionBuilder("perm2", PermissionScope.SELECTED_SUB,  app1)
			.build();
		
		assertFalse(authorizationServices.hasPermission(person, ou, perm12, app1));
		
		// has permission in super-org-unit:
		IAuthorizableOrgUnit rootOu = new AuthOrgUnitSimple(
			new OrgUnitSimpleBuilder()
		);
		
		IAuthorizableOrgUnit sub_ou = new AuthOrgUnitSimple(
			new OrgUnitSimpleBuilder(rootOu)
				.superOuId(OU_ID)
		);
		
		Mockito.when(securityDomainServices.findAuthOrgUnitById(OU_ID)).thenReturn(ou);
		
		assertTrue(authorizationServices.hasPermission(person, sub_ou, perm1, app1));
	}

	@Test
	public void testHasPermissionStringStringStringIApplication()
	{
		IApplication app = new OpenURApplicationBuilder("app", "app", "app_pw")
			.build();		
		final String PERM_NAME_1 = "perm1";
		IPermission perm1 = new OpenURPermissionBuilder(PERM_NAME_1, PermissionScope.SELECTED,  app)
			.build();		
		IRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm1)))
			.build();
		
		final String PERS_ID = UUID.randomUUID().toString();		
		IPerson person = new PersonBuilder(PERS_ID, "username1", "password1")
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		IAuthorizableMember member = new AuthorizableMember(person, OU_ID, Arrays.asList(role1));		
		IAuthorizableOrgUnit ou = new AuthorizableOrgUnit(
			new OrganizationalUnitBuilder(OU_ID), Arrays.asList(member));
		
		Mockito.when(securityDomainServices.findPermissionByName(PERM_NAME_1, app)).thenReturn(perm1);
		Mockito.when(securityDomainServices.findAuthOrgUnitById(OU_ID)).thenReturn(ou);
		Mockito.when(userServices.findPersonById(PERS_ID)).thenReturn(person);
		
		// has permission in org-unit:
		assertTrue(authorizationServices.hasPermission(PERS_ID, OU_ID, PERM_NAME_1, app));
		
		// doesn't have permission:
		final String PERM_NAME_2 = "perm2";
		IPermission perm2 = new OpenURPermissionBuilder(PERM_NAME_2, PermissionScope.SELECTED_SUB,  app)
			.build();
		Mockito.when(securityDomainServices.findPermissionByName(PERM_NAME_2, app)).thenReturn(perm2);
		assertFalse(authorizationServices.hasPermission(PERS_ID, OU_ID, PERM_NAME_2, app));
		
		// has permission in super-org-unit:
		IAuthorizableOrgUnit rootOu = new AuthOrgUnitSimple(
			new OrgUnitSimpleBuilder()
		);
		
		final String SUB_OU_ID = UUID.randomUUID().toString();	
		IAuthorizableOrgUnit sub_ou = new AuthOrgUnitSimple(
			new OrgUnitSimpleBuilder(SUB_OU_ID, rootOu)
				.superOuId(OU_ID)
		);
		
		Mockito.when(securityDomainServices.findAuthOrgUnitById(SUB_OU_ID)).thenReturn(sub_ou);
		
		assertTrue(authorizationServices.hasPermission(PERS_ID, SUB_OU_ID, PERM_NAME_1, app));
	}
}
