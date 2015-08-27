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
import org.openur.domain.testfixture.dummyimpl.MyApplicationImpl;
import org.openur.domain.testfixture.dummyimpl.MyAuthorizableMember;
import org.openur.domain.testfixture.dummyimpl.MyAuthorizableOrgUnit;
import org.openur.domain.testfixture.dummyimpl.MyPermissionImpl;
import org.openur.domain.testfixture.dummyimpl.MyPerson;
import org.openur.domain.testfixture.dummyimpl.MyRoleImpl;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
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
	public void testHasPermissionIPersonIOrganizationalUnitIPermissionIApplication()
	{
		// test with open-ur-specific domain-objects:
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.SUPER_OU_UUID_1)).thenReturn(TestObjectContainer.SUPER_OU_1);		
		
		// has permission in org-unit:
		assertTrue(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_1, TestObjectContainer.ORG_UNIT_A, TestObjectContainer.PERMISSION_1_A, TestObjectContainer.APP_A));
		
		// doesn't have permission:
		assertFalse(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_1, TestObjectContainer.ORG_UNIT_A, TestObjectContainer.PERMISSION_1_C, TestObjectContainer.APP_A));
		
		// has permission in super-org-unit:
		assertTrue(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_3, TestObjectContainer.ORG_UNIT_A, TestObjectContainer.PERMISSION_2_C, TestObjectContainer.APP_C));
		
		// doesn't have permission in super-org-unit:
		assertFalse(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_3, TestObjectContainer.ORG_UNIT_A, TestObjectContainer.PERMISSION_1_A, TestObjectContainer.APP_A));
		
		// test with arbitrary domain-objects:
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
		// test with open-ur-specific domain-objects:
		Mockito.when(securityDaoMock.findPermissionByText(TestObjectContainer.PERMISSION_1_A.getPermissionText()))
				.thenReturn(TestObjectContainer.PERMISSION_1_A);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_3)).thenReturn(TestObjectContainer.PERSON_3);
		Mockito.when(securityDaoMock.findPermissionByText(TestObjectContainer.PERMISSION_2_C.getPermissionText()))
				.thenReturn(TestObjectContainer.PERMISSION_2_C);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.SUPER_OU_UUID_1)).thenReturn(TestObjectContainer.SUPER_OU_1);		
		
		// has permission in org-unit:
		assertTrue(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_1, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A));
		
		// doesn't have permission:
		assertFalse(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_1, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_1_C.getPermissionText(), TestObjectContainer.APP_A));
		
		// has permission in super-org-unit:
		assertTrue(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_3, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_2_C.getPermissionText(), TestObjectContainer.APP_C));
		
		// doesn't have permission in super-org-unit:
		assertFalse(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_3, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A));
		
		// test with arbitrary domain-objects:
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
		
		Mockito.when(securityDaoMock.findPermissionByText(PERM_1_NAME)).thenReturn(perm1);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(OU_ID)).thenReturn(ou);
		Mockito.when(userServicesMock.findPersonById(PERSON_ID)).thenReturn(person);
		
		// has permission in org-unit:
		assertTrue(authorizationServices.hasPermission(PERSON_ID, OU_ID, PERM_1_NAME, app));
		
		// doesn't have permission:
		final String PERM_2_ID = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_2_ID, PERM_2_NAME, app);
		
		Mockito.when(securityDaoMock.findPermissionByText(PERM_2_NAME)).thenReturn(perm2);
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
