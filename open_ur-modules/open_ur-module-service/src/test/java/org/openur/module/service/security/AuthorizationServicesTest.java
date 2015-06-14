package org.openur.module.service.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.domain.test.dummyimpl.MyApplicationImpl;
import org.openur.domain.test.dummyimpl.MyAuthorizableMember;
import org.openur.domain.test.dummyimpl.MyAuthorizableOrgUnit;
import org.openur.domain.test.dummyimpl.MyPermissionImpl;
import org.openur.domain.test.dummyimpl.MyPerson;
import org.openur.domain.test.dummyimpl.MyRoleImpl;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.service.config.SecurityTestSpringConfig;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testSecurityServices")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class AuthorizationServicesTest
{
	@Inject
	private IAuthorizationServices authorizationServices;
	
	@Inject
	private IUserServices userServicesMock;
	
	@Inject
	private IOrgUnitServices orgUnitServicesMock;
	
	@Inject
	private ISecurityDao securityDaoMock;

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
		final String APP_ID = UUID.randomUUID().toString();
		final String APP_NAME = "appName";
		MyApplicationImpl app = new MyApplicationImpl(APP_ID, APP_NAME);
		
		final String PERM_1_ID = UUID.randomUUID().toString();
		final String PERM_1_NAME = "perm1Name";
		MyPermissionImpl perm1 = new MyPermissionImpl(PERM_1_ID, PERM_1_NAME, app);
		
		final String ROLE_ID = UUID.randomUUID().toString();
		final String ROLE_NAME = "roleName";
		MyRoleImpl role = new MyRoleImpl(ROLE_ID, ROLE_NAME);
		role.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(perm1)));
		
		final String PERSON_ID = UUID.randomUUID().toString();
		final String PERSON_NUMBER = "personNumber";
		MyPerson person = new MyPerson(PERSON_ID, PERSON_NUMBER);
		person.addApplication(app);
		
		final String OU_ID = UUID.randomUUID().toString();
		MyAuthorizableMember member = new MyAuthorizableMember(person, OU_ID);
		member.addRole(role);
		
		final String OU_NAME = "ouName";
		MyAuthorizableOrgUnit ou = new MyAuthorizableOrgUnit(OU_ID, OU_NAME);
		ou.addMember(member);
		
		// has permission in org-unit:
		assertTrue(authorizationServices.hasPermission(person, ou, perm1, app));
		
		// doesn't have permission:
		final String PERM_2_ID = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_2_ID, PERM_2_NAME, app);
		
		assertFalse(authorizationServices.hasPermission(person, ou, perm2, app));
		
		// has permission in super-org-unit:
		final String SUB_OU_ID = UUID.randomUUID().toString();
		final String SUB_OU_NAME = "subOuName";
		MyAuthorizableOrgUnit subOu = new MyAuthorizableOrgUnit(SUB_OU_ID, SUB_OU_NAME);
		subOu.setSuperOrgUnit(ou);
		
		Mockito.when(orgUnitServicesMock.findOrgUnitById(OU_ID)).thenReturn(ou);
		
		assertTrue(authorizationServices.hasPermission(person, subOu, perm1, app));
	}

	@Test
	public void testHasPermissionStringStringStringIApplication()
	{
		final String APP_ID = UUID.randomUUID().toString();
		final String APP_NAME = "appName";
		MyApplicationImpl app = new MyApplicationImpl(APP_ID, APP_NAME);
		
		final String PERM_1_ID = UUID.randomUUID().toString();
		final String PERM_1_NAME = "perm1Name";
		MyPermissionImpl perm1 = new MyPermissionImpl(PERM_1_ID, PERM_1_NAME, app);
		
		final String ROLE_ID = UUID.randomUUID().toString();
		final String ROLE_NAME = "roleName";
		MyRoleImpl role = new MyRoleImpl(ROLE_ID, ROLE_NAME);
		role.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(perm1)));
		
		final String PERSON_ID = UUID.randomUUID().toString();
		final String PERSON_NUMBER = "personNumber";
		MyPerson person = new MyPerson(PERSON_ID, PERSON_NUMBER);
		person.addApplication(app);
		
		final String OU_ID = UUID.randomUUID().toString();
		MyAuthorizableMember member = new MyAuthorizableMember(person, OU_ID);
		member.addRole(role);
		
		final String OU_NAME = "ouName";
		MyAuthorizableOrgUnit ou = new MyAuthorizableOrgUnit(OU_ID, OU_NAME);
		ou.addMember(member);
		
		Mockito.when(securityDaoMock.findPermissionByName(PERM_1_NAME)).thenReturn(perm1);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(OU_ID)).thenReturn(ou);
		Mockito.when(userServicesMock.findPersonById(PERSON_ID)).thenReturn(person);
		
		// has permission in org-unit:
		assertTrue(authorizationServices.hasPermission(PERSON_ID, OU_ID, PERM_1_NAME, app));
		
		// doesn't have permission:
		final String PERM_2_ID = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_2_ID, PERM_2_NAME, app);
		
		Mockito.when(securityDaoMock.findPermissionByName(PERM_2_NAME)).thenReturn(perm2);
		assertFalse(authorizationServices.hasPermission(PERSON_ID, OU_ID, PERM_2_NAME, app));
		
		// has permission in super-org-unit:	
		final String SUB_OU_ID = UUID.randomUUID().toString();
		final String SUB_OU_NAME = "subOuName";
		MyAuthorizableOrgUnit subOu = new MyAuthorizableOrgUnit(SUB_OU_ID, SUB_OU_NAME);
		subOu.setSuperOrgUnit(ou);
		
		Mockito.when(orgUnitServicesMock.findOrgUnitById(SUB_OU_ID)).thenReturn(subOu);
		
		assertTrue(authorizationServices.hasPermission(PERSON_ID, SUB_OU_ID, PERM_1_NAME, app));
	}
}
