package org.openur.module.service.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Before;
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
	
	// fields for arbitrary domain-objects:
	private final String APP_ID = UUID.randomUUID().toString();
	private final String APP_NAME = "appName";
	private MyApplicationImpl app;
	private final String PERM_1_ID = UUID.randomUUID().toString();
	private final String PERM_1_NAME = "perm1Name";
	private MyPermissionImpl perm1;
	private final String ROLE_ID = UUID.randomUUID().toString();
	private final String ROLE_NAME = "roleName";
	private MyRoleImpl role;
	private final String PERSON_ID = UUID.randomUUID().toString();
	private final String PERSON_NUMBER = "personNumber";
	private MyPerson person;
	private final String OU_ID = UUID.randomUUID().toString();
	private MyAuthorizableMember member;
	private final String OU_NAME = "ouName";
	private MyAuthorizableOrgUnit ou;
	private final String SUB_OU_ID = UUID.randomUUID().toString();
	private final String SUB_OU_NAME = "subOuName";
	private MyAuthorizableOrgUnit subOu;
	
	@Before
	public void setUp()
	{
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_3)).thenReturn(TestObjectContainer.PERSON_3);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.SUPER_OU_UUID_1)).thenReturn(TestObjectContainer.SUPER_OU_1);		
		Mockito.when(securityDaoMock.findPermission(TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()))
				.thenReturn(TestObjectContainer.PERMISSION_1_A);
		Mockito.when(securityDaoMock.findPermission(TestObjectContainer.PERMISSION_1_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()))
				.thenReturn(TestObjectContainer.PERMISSION_1_C);
		Mockito.when(securityDaoMock.findPermission(TestObjectContainer.PERMISSION_2_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()))
				.thenReturn(TestObjectContainer.PERMISSION_2_C);

		// init arbitrary domain-objects:
		app = new MyApplicationImpl(APP_ID, APP_NAME);		
		perm1 = new MyPermissionImpl(PERM_1_ID, PERM_1_NAME, app);
		role = new MyRoleImpl(ROLE_ID, ROLE_NAME);
		role.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(perm1)));
		person = new MyPerson(PERSON_ID, PERSON_NUMBER);
		person.addApplication(app);
		member = new MyAuthorizableMember(person, OU_ID);
		member.addRole(role);
		ou = new MyAuthorizableOrgUnit(OU_ID, OU_NAME);
		ou.addMember(member);
		subOu = new MyAuthorizableOrgUnit(SUB_OU_ID, SUB_OU_NAME);
		subOu.setSuperOrgUnit(ou);
		
		Mockito.when(securityDaoMock.findPermission(PERM_1_NAME, APP_NAME)).thenReturn(perm1);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(OU_ID)).thenReturn(ou);
		Mockito.when(userServicesMock.findPersonById(PERSON_ID)).thenReturn(person);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(SUB_OU_ID)).thenReturn(subOu);
	}

	@Test
	public void testHasPermissionInOrgUnit()
	{
		// test with standard open-ur domain-objects:
		assertTrue(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_1, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()));
		
		// test with arbitrary domain-objects:
		assertTrue(authorizationServices.hasPermission(PERSON_ID, OU_ID, PERM_1_NAME, app.getApplicationName()));
	}

	@Test
	public void testHasNotPermission()
	{		
		// test with standard open-ur domain-objects:
		assertFalse(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_1, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_1_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()));
		
		// test with arbitrary domain-objects:
		final String PERM_2_ID = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_2_ID, PERM_2_NAME, app);
		Mockito.when(securityDaoMock.findPermission(PERM_2_NAME, APP_NAME)).thenReturn(perm2);
		assertFalse(authorizationServices.hasPermission(PERSON_ID, OU_ID, PERM_2_NAME, APP_NAME));
	}

	@Test
	public void testHasPermissionInSuperOrgUnit()
	{		
		// test with standard open-ur domain-objects:
		assertTrue(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_3, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_2_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()));
		
		// test with arbitrary domain-objects:
		assertTrue(authorizationServices.hasPermission(PERSON_ID, SUB_OU_ID, PERM_1_NAME, APP_NAME));
	}

	@Test
	public void testHasNotPermissionInSuperOrgUnit()
	{		
		// test with standard open-ur domain-objects:
		assertFalse(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_3, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()));
		
		// test with arbitrary domain-objects:
		final String PERM_2_ID = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_2_ID, PERM_2_NAME, app);
		Mockito.when(securityDaoMock.findPermission(PERM_2_NAME, APP_NAME)).thenReturn(perm2);
		assertFalse(authorizationServices.hasPermission(PERSON_ID, SUB_OU_ID, PERM_2_NAME, APP_NAME));
	}

	@Test(expected=NullPointerException.class)
	public void testNoPersonFoundForId()
	{		
		authorizationServices.hasPermission("somePersonId", SUB_OU_ID, PERM_1_NAME, app.getApplicationName());
	}

	@Test(expected=NullPointerException.class)
	public void testNoOrgUnitFoundForId()
	{		
		authorizationServices.hasPermission(PERSON_ID, "someOrgUnitId", PERM_1_NAME, app.getApplicationName());
	}

	@Test(expected=NullPointerException.class)
	public void testNoPermissionFoundWithGivenText()
	{		
		authorizationServices.hasPermission(PERSON_ID, SUB_OU_ID, "somePermissionText", app.getApplicationName());
	}
}
