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
import org.openur.domain.testfixture.dummyimpl.MyOrgUnitMember;
import org.openur.domain.testfixture.dummyimpl.MyOrgUnit;
import org.openur.domain.testfixture.dummyimpl.MyAuthorizableTechUser;
import org.openur.domain.testfixture.dummyimpl.MyPermissionImpl;
import org.openur.domain.testfixture.dummyimpl.MyPerson;
import org.openur.domain.testfixture.dummyimpl.MyRoleImpl;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.persistence.dao.ISecurityDomainDao;
import org.openur.module.service.config.SecurityTestSpringConfig;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.module.util.exception.EntityNotFoundException;
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
	private ISecurityDomainDao securityDaoMock;
	
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
	private static final String TECH_USER_ID = UUID.randomUUID().toString();
	
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
	private MyOrgUnitMember member;
	private MyOrgUnitMember memberInSuperOu;
	private MyOrgUnitMember memberInRootOu;
	private MyOrgUnit ou;
	private MyOrgUnit otherOu;
	private MyOrgUnit superOu;
	private MyOrgUnit rootOu;
	private MyAuthorizableTechUser techUser; 
	
	@Before
	public void setUp()
	{
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_3)).thenReturn(TestObjectContainer.PERSON_3);
		Mockito.when(userServicesMock.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_1)).thenReturn(TestObjectContainer.TECH_USER_1);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_C, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_C);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.SUPER_OU_UUID_1, Boolean.TRUE)).thenReturn(TestObjectContainer.SUPER_OU_1);		
		Mockito.when(securityDaoMock.findPermission(TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()))
				.thenReturn(TestObjectContainer.PERMISSION_1_A);
		Mockito.when(securityDaoMock.findPermission(TestObjectContainer.PERMISSION_2_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()))
				.thenReturn(TestObjectContainer.PERMISSION_2_A);
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
		memberInRootOu = new MyOrgUnitMember(personInRoot, ROOT_OU_ID);
		memberInRootOu.addRole(roleInRootOu);
		rootOu = new MyOrgUnit(ROOT_OU_ID, "rootOuNumber");
		rootOu.addMember(memberInRootOu);

		person = new MyPerson(PERSON_ID, "personNumber");
		person.addApplication(app);

		permissionInSuperOu = new MyPermissionImpl(UUID.randomUUID().toString(), PERMISSION_IN_SUPER_OU_TEXT, app);
		roleInSuperOu = new MyRoleImpl(UUID.randomUUID().toString(), "roleInSuperOuName");
		roleInSuperOu.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(permissionInSuperOu)));
		memberInSuperOu = new MyOrgUnitMember(person, SUPER_OU_ID);
		memberInSuperOu.addRole(roleInSuperOu);
		superOu = new MyOrgUnit(SUPER_OU_ID, "superOuNumber");
		superOu.setSuperOrgUnit(rootOu);
		superOu.setRootOrgUnit(rootOu);
		superOu.addMember(memberInSuperOu);
		
		permission = new MyPermissionImpl(PERM_ID, PERMISSION_TEXT, app);
		role = new MyRoleImpl(UUID.randomUUID().toString(), "roleName");
		role.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(permission)));
		member = new MyOrgUnitMember(person, OU_ID);
		member.addRole(role);
		ou = new MyOrgUnit(OU_ID, "ouNumber");
		ou.setSuperOrgUnit(superOu);
		ou.setRootOrgUnit(rootOu);
		ou.addMember(member);
		
		otherPermission = new MyPermissionImpl(UUID.randomUUID().toString(), OTHER_PERMISSION_TEXT, app);
		
		otherOu = new MyOrgUnit(OTHER_OU_ID, "otherOuNumber");
		otherOu.setSuperOrgUnit(superOu);
		
		techUser = new MyAuthorizableTechUser(TECH_USER_ID, "techUserNumber");
		techUser.addPermissionSet(app, new HashSet<MyPermissionImpl>(Arrays.asList(permission, otherPermission)));
		
		Mockito.when(securityDaoMock.findPermission(PERMISSION_TEXT, APP_NAME)).thenReturn(permission);
		Mockito.when(securityDaoMock.findPermission(PERMISSION_IN_SUPER_OU_TEXT, APP_NAME)).thenReturn(permissionInSuperOu);
		Mockito.when(securityDaoMock.findPermission(OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(otherPermission);
		Mockito.when(securityDaoMock.findPermission(PERMISSION_IN_ROOT_OU_TEXT, APP_NAME)).thenReturn(permissionInRootOu);
		Mockito.when(userServicesMock.findPersonById(PERSON_ID)).thenReturn(person);
		Mockito.when(userServicesMock.findPersonById(PERSON_IN_ROOT_ID)).thenReturn(personInRoot);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(SUPER_OU_ID, Boolean.TRUE)).thenReturn(superOu);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(OU_ID, Boolean.TRUE)).thenReturn(ou);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(OTHER_OU_ID, Boolean.TRUE)).thenReturn(otherOu);
		Mockito.when(orgUnitServicesMock.obtainRootOrgUnits()).thenReturn(new HashSet<IOrganizationalUnit>(Arrays.asList(rootOu)));
		Mockito.when(userServicesMock.findTechnicalUserById(TECH_USER_ID)).thenReturn(techUser);
	}

	@Test
	public void testHasPermissionInOrgUnit()
		throws EntityNotFoundException
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
		throws EntityNotFoundException
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
		throws EntityNotFoundException
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
		throws EntityNotFoundException
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
		throws EntityNotFoundException
	{		
		// only test with arbitrary objects:		
		// has permmission:
		assertTrue(authorizationServices.hasPermission(PERSON_IN_ROOT_ID, PERMISSION_IN_ROOT_OU_TEXT, APP_NAME));		
		
		// has not permission:
		assertFalse(authorizationServices.hasPermission(PERSON_IN_ROOT_ID, OTHER_PERMISSION_TEXT, APP_NAME));		
	}

	@Test
	public void testHasPermissionTechUser()
		throws EntityNotFoundException
	{		
		// test with standard open-ur domain-objects:
		assertTrue(authorizationServices.hasPermissionTechUser(TestObjectContainer.TECH_USER_UUID_1, 
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()));
		assertTrue(authorizationServices.hasPermissionTechUser(TestObjectContainer.TECH_USER_UUID_1, 
				TestObjectContainer.PERMISSION_2_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()));
		assertFalse(authorizationServices.hasPermissionTechUser(TestObjectContainer.TECH_USER_UUID_1, 
				TestObjectContainer.PERMISSION_1_C.getPermissionText(), TestObjectContainer.APP_C.getApplicationName()));
		
		// test with arbitrary domain-objects:
		assertTrue(authorizationServices.hasPermissionTechUser(TECH_USER_ID, PERMISSION_TEXT, APP_NAME));
		assertTrue(authorizationServices.hasPermissionTechUser(TECH_USER_ID, OTHER_PERMISSION_TEXT, APP_NAME));
		assertFalse(authorizationServices.hasPermissionTechUser(TECH_USER_ID, PERMISSION_IN_SUPER_OU_TEXT, APP_NAME));
	}

	@Test(expected=EntityNotFoundException.class)
	public void testNoPersonFoundForId()
		throws EntityNotFoundException
	{		
		authorizationServices.hasPermission("someUnknownPersonId", SUPER_OU_ID, PERMISSION_TEXT, app.getApplicationName());
	}

	@Test(expected=EntityNotFoundException.class)
	public void testNoOrgUnitFoundForId()
		throws EntityNotFoundException
	{		
		authorizationServices.hasPermission(PERSON_ID, "someUnknownOuId", PERMISSION_TEXT, app.getApplicationName());
	}

	@Test(expected=EntityNotFoundException.class)
	public void testNoPermissionFoundWithGivenText()
		throws EntityNotFoundException
	{		
		authorizationServices.hasPermission(PERSON_ID, SUPER_OU_ID, "someUnknownPermissionText", app.getApplicationName());
	}

	@Test(expected=EntityNotFoundException.class)
	public void testNoApplicationFoundWithGivenName()
		throws EntityNotFoundException
	{		
		authorizationServices.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, "someUnknownApplicationName");		
	}

	@Test(expected=EntityNotFoundException.class)
	public void testNoTechUserFoundForId()
		throws EntityNotFoundException
	{		
		authorizationServices.hasPermissionTechUser("someUnknownTechUserId", PERMISSION_TEXT, app.getApplicationName());
	}

	@Test(expected=EntityNotFoundException.class)
	public void testCheckPermissionTechUserButGivePersonId()
		throws EntityNotFoundException
	{		
		// check tech-user-permission with valid permission-text but with person-id:
		authorizationServices.hasPermissionTechUser(PERSON_ID, PERMISSION_TEXT, app.getApplicationName());
	}
}
