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
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
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
	private static final String APP_ID = UUID.randomUUID().toString();
	private static final String APP_NAME = "appName";
	private static final String PERM_ID = UUID.randomUUID().toString();
	private static final String PERMISSION_TEXT = "permissionText";
	private static final String PERMISSION_IN_SUPER_OU_TEXT = "permissionInSuperOuText";
	private static final String PERMISSION_IN_ROOT_OU_TEXT = "permissionInRootOuText";
	private static final String OTHER_PERMISSION_TEXT = "otherPermissionText";
	private static final String PERSON_ID = UUID.randomUUID().toString();
	private static final String PERSON_IN_ROOT_ID = UUID.randomUUID().toString();
	private static final String OU_ID = UUID.randomUUID().toString();
	private static final String OTHER_OU_ID = UUID.randomUUID().toString();
	private static final String SUPER_OU_ID = UUID.randomUUID().toString();
	private static final String ROOT_OU_ID = UUID.randomUUID().toString();
	
	private MyApplicationImpl app;
	private MyPermissionImpl permission;
	private MyPermissionImpl permissionInSuperOu;
	private MyPermissionImpl permissionInRootOu;
	private MyPermissionImpl otherPermission;
	private MyRoleImpl role;
	private MyRoleImpl roleInSuperOu;
	private MyRoleImpl roleInRootOu;
	private MyPerson person;
	private MyPerson personInRoot;
	private MyAuthorizableMember member;
	private MyAuthorizableMember memberInSuperOu;
	private MyAuthorizableMember memberInRootOu;
	private MyAuthorizableOrgUnit ou;
	private MyAuthorizableOrgUnit otherOu;
	private MyAuthorizableOrgUnit superOu;
	private MyAuthorizableOrgUnit rootOu;
	
	@Before
	public void setUp()
	{
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_3)).thenReturn(TestObjectContainer.PERSON_3);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_C, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_C);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.SUPER_OU_UUID_1, Boolean.TRUE)).thenReturn(TestObjectContainer.SUPER_OU_1);		
		Mockito.when(securityDaoMock.findPermission(TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()))
				.thenReturn(TestObjectContainer.PERMISSION_1_A);
		Mockito.when(securityDaoMock.findPermission(TestObjectContainer.PERMISSION_1_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()))
				.thenReturn(TestObjectContainer.PERMISSION_1_C);
		Mockito.when(securityDaoMock.findPermission(TestObjectContainer.PERMISSION_2_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()))
				.thenReturn(TestObjectContainer.PERMISSION_2_C);

		// init arbitrary domain-objects:
		app = new MyApplicationImpl(APP_ID, APP_NAME);

		personInRoot = new MyPerson(PERSON_IN_ROOT_ID, "personInRootNumber");
		personInRoot.addApplication(app);

		permissionInRootOu = new MyPermissionImpl(UUID.randomUUID().toString(), PERMISSION_IN_ROOT_OU_TEXT, app);
		roleInRootOu = new MyRoleImpl(UUID.randomUUID().toString(), "roleInRootOuName");
		roleInRootOu.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(permissionInRootOu)));
		memberInRootOu = new MyAuthorizableMember(personInRoot, ROOT_OU_ID);
		memberInRootOu.addRole(roleInRootOu);
		rootOu = new MyAuthorizableOrgUnit(ROOT_OU_ID, "rootOuNumber");
		rootOu.addMember(memberInRootOu);

		person = new MyPerson(PERSON_ID, "personNumber");
		person.addApplication(app);

		permissionInSuperOu = new MyPermissionImpl(UUID.randomUUID().toString(), PERMISSION_IN_SUPER_OU_TEXT, app);
		roleInSuperOu = new MyRoleImpl(UUID.randomUUID().toString(), "roleInSuperOuName");
		roleInSuperOu.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(permissionInSuperOu)));
		memberInSuperOu = new MyAuthorizableMember(person, SUPER_OU_ID);
		memberInSuperOu.addRole(roleInSuperOu);
		superOu = new MyAuthorizableOrgUnit(SUPER_OU_ID, "superOuNumber");
		superOu.setSuperOrgUnit(rootOu);
		superOu.setRootOrgUnit(rootOu);
		superOu.addMember(memberInSuperOu);
		
		permission = new MyPermissionImpl(PERM_ID, PERMISSION_TEXT, app);
		role = new MyRoleImpl(UUID.randomUUID().toString(), "roleName");
		role.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(permission)));
		member = new MyAuthorizableMember(person, OU_ID);
		member.addRole(role);
		ou = new MyAuthorizableOrgUnit(OU_ID, "ouNumber");
		ou.setSuperOrgUnit(superOu);
		ou.setRootOrgUnit(rootOu);
		ou.addMember(member);
		
		otherPermission = new MyPermissionImpl(UUID.randomUUID().toString(), OTHER_PERMISSION_TEXT, app);
		
		otherOu = new MyAuthorizableOrgUnit(OTHER_OU_ID, "otherOuNumber");
		otherOu.setSuperOrgUnit(superOu);
		
		Mockito.when(securityDaoMock.findPermission(PERMISSION_TEXT, APP_NAME)).thenReturn(permission);
		Mockito.when(securityDaoMock.findPermission(PERMISSION_IN_SUPER_OU_TEXT, APP_NAME)).thenReturn(permissionInSuperOu);
		Mockito.when(securityDaoMock.findPermission(OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(otherPermission);
		Mockito.when(securityDaoMock.findPermission(PERMISSION_IN_ROOT_OU_TEXT, APP_NAME)).thenReturn(permissionInRootOu);
		Mockito.when(userServicesMock.findPersonById(PERSON_ID)).thenReturn(person);
		Mockito.when(userServicesMock.findPersonById(PERSON_IN_ROOT_ID)).thenReturn(personInRoot);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(SUPER_OU_ID, Boolean.TRUE)).thenReturn(superOu);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(OU_ID, Boolean.TRUE)).thenReturn(ou);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(OTHER_OU_ID, Boolean.TRUE)).thenReturn(otherOu);
		Mockito.when(orgUnitServicesMock.obtainRootOrgUnits()).thenReturn(new HashSet<IAuthorizableOrgUnit>(Arrays.asList(rootOu)));
	}

	@Test
	public void testHasPermissionInOrgUnit()
	{
		// test with standard open-ur domain-objects:
		assertTrue(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_1, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()));
		
		// test with arbitrary domain-objects:
		assertTrue(authorizationServices.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, app.getApplicationName()));
	}

	@Test
	public void testMemberInOrgUnitButHasNotPermission()
	{		
		// test with standard open-ur domain-objects:
		assertFalse(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_1, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_1_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()));
		
		// test with arbitrary domain-objects:
		assertFalse(authorizationServices.hasPermission(PERSON_ID, OU_ID, OTHER_PERMISSION_TEXT, APP_NAME));
	}

	@Test
	public void testNotMemberInOrgUnitAndThusHasNotPermission()
	{		
		// test with standard open-ur domain-objects:
		assertFalse(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_1, TestObjectContainer.ORG_UNIT_UUID_C, 
				TestObjectContainer.PERMISSION_1_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()));
		
		// test with arbitrary domain-objects:
		assertFalse(authorizationServices.hasPermission(PERSON_ID, OTHER_OU_ID, OTHER_PERMISSION_TEXT, APP_NAME));
	}

	@Test
	public void testHasPermissionInSuperOrgUnit()
	{		
		// test with standard open-ur domain-objects:
		assertTrue(authorizationServices.hasPermission(
				TestObjectContainer.PERSON_UUID_3, TestObjectContainer.ORG_UNIT_UUID_A, 
				TestObjectContainer.PERMISSION_2_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()));
		
		// test with arbitrary domain-objects:
		assertTrue(authorizationServices.hasPermission(PERSON_ID, OTHER_OU_ID, PERMISSION_IN_SUPER_OU_TEXT, APP_NAME));
	}

	@Test
	public void testCheckSystemWidePermissions()
	{		
		// only test with arbitrary objects:		
		// has permmission:
		assertTrue(authorizationServices.hasPermission(PERSON_IN_ROOT_ID, PERMISSION_IN_ROOT_OU_TEXT, APP_NAME));		
		
		// has not permission:
		assertFalse(authorizationServices.hasPermission(PERSON_IN_ROOT_ID, OTHER_PERMISSION_TEXT, APP_NAME));		
	}

	@Test(expected=NullPointerException.class)
	public void testNoPersonFoundForId()
	{		
		authorizationServices.hasPermission("somePersonId", SUPER_OU_ID, PERMISSION_TEXT, app.getApplicationName());
	}

	@Test(expected=NullPointerException.class)
	public void testNoOrgUnitFoundForId()
	{		
		authorizationServices.hasPermission(PERSON_ID, "someOrgUnitId", PERMISSION_TEXT, app.getApplicationName());
	}

	@Test(expected=NullPointerException.class)
	public void testNoPermissionFoundWithGivenText()
	{		
		authorizationServices.hasPermission(PERSON_ID, SUPER_OU_ID, "somePermissionText", app.getApplicationName());
	}
}
