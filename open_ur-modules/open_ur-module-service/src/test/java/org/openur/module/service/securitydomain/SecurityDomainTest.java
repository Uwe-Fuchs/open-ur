package org.openur.module.service.securitydomain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.domain.testfixture.dummyimpl.MyApplicationImpl;
import org.openur.domain.testfixture.dummyimpl.MyPermissionImpl;
import org.openur.domain.testfixture.dummyimpl.MyRoleImpl;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.persistence.dao.ISecurityDomainDao;
import org.openur.module.service.config.SecurityTestSpringConfig;
import org.openur.module.service.securitydomain.ISecurityDomainServices;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testSecurityServices")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class SecurityDomainTest
{
	@Inject
	private ISecurityDomainDao securityDao;
	
	@Inject
	private ISecurityDomainServices securityDomainServices;

	@Test
	public void testObtainAllRoles()
	{	
		// test with open-ur-specific domain-objects:
		Mockito.when(securityDao.obtainAllRoles()).thenReturn(Arrays.asList(TestObjectContainer.ROLE_X, TestObjectContainer.ROLE_Y));
		
		Set<IRole> resultSet = securityDomainServices.obtainAllRoles();
		
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(TestObjectContainer.ROLE_X));
		assertTrue(resultSet.contains(TestObjectContainer.ROLE_Y));
		assertFalse(resultSet.contains(TestObjectContainer.ROLE_Z));

		// test with arbitrary domain-objects:
		final String ROLE_1_ID = UUID.randomUUID().toString();
		final String ROLE_1_NAME = "role1Name";
		IRole role1 = new MyRoleImpl(ROLE_1_ID, ROLE_1_NAME);
		
		final String ROLE_2_ID = UUID.randomUUID().toString();
		final String ROLE_2_NAME = "role2Name";
		IRole role2 = new MyRoleImpl(ROLE_2_ID, ROLE_2_NAME);

		Mockito.when(securityDao.obtainAllRoles()).thenReturn(Arrays.asList(role1, role2));
		
		resultSet = securityDomainServices.obtainAllRoles();
		
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		
		for (IRole r : resultSet)
		{
			assertTrue(ROLE_1_ID.equals(r.getIdentifier()) || ROLE_2_ID.equals(r.getIdentifier()));
			assertTrue(ROLE_1_NAME.equals(r.getRoleName()) || ROLE_2_NAME.equals(r.getRoleName()));
		}
		
		final String OTHER_ID = UUID.randomUUID().toString();
		
		for (IRole r : resultSet)
		{
			assertFalse(OTHER_ID.equals(r.getIdentifier()));
		}
	}

	@Test
	public void testFindRoleById()
	{
		// test with open-ur-specific domain-objects:
		Mockito.when(securityDao.findRoleById(TestObjectContainer.ROLE_X.getIdentifier())).thenReturn(TestObjectContainer.ROLE_X);		
		IRole resultRole = securityDomainServices.findRoleById(TestObjectContainer.ROLE_X.getIdentifier());
		assertEquals(TestObjectContainer.ROLE_X, resultRole);

		Mockito.when(securityDao.findRoleById(TestObjectContainer.ROLE_Y.getIdentifier())).thenReturn(TestObjectContainer.ROLE_Y);
		resultRole = securityDomainServices.findRoleById(TestObjectContainer.ROLE_Y.getIdentifier());
		assertEquals(TestObjectContainer.ROLE_Y, resultRole);
		
		String otherId = UUID.randomUUID().toString();
		resultRole = securityDomainServices.findRoleById(otherId);
		assertTrue(resultRole == null);

		// test with arbitrary domain-objects:
		final String ROLE_1_ID = UUID.randomUUID().toString();
		final String ROLE_1_NAME = "role1Name";
		IRole role1 = new MyRoleImpl(ROLE_1_ID, ROLE_1_NAME);
		
		Mockito.when(securityDao.findRoleById(ROLE_1_ID)).thenReturn(role1);
		
		resultRole = securityDomainServices.findRoleById(ROLE_1_ID);
		assertEquals(ROLE_1_ID, resultRole.getIdentifier());
		assertEquals(ROLE_1_NAME, resultRole.getRoleName());

		final String ROLE_2_ID = UUID.randomUUID().toString();
		final String ROLE_2_NAME = "role2Name";
		IRole role2 = new MyRoleImpl(ROLE_2_ID, ROLE_2_NAME);
		
		Mockito.when(securityDao.findRoleById(ROLE_2_ID)).thenReturn(role2);
		
		resultRole = securityDomainServices.findRoleById(ROLE_2_ID);
		assertEquals(ROLE_2_ID, resultRole.getIdentifier());
		assertEquals(ROLE_2_NAME, resultRole.getRoleName());
		
		otherId = UUID.randomUUID().toString();
		resultRole = securityDomainServices.findRoleById(otherId);
		assertTrue(resultRole == null);
	}

	@Test
	public void testFindRoleByName()
	{
		// test with open-ur-specific domain-objects:
		Mockito.when(securityDao.findRoleByName(TestObjectContainer.ROLE_X.getRoleName())).thenReturn(TestObjectContainer.ROLE_X);		
		IRole resultRole = securityDomainServices.findRoleByName(TestObjectContainer.ROLE_X.getRoleName());
		assertEquals(TestObjectContainer.ROLE_X, resultRole);

		Mockito.when(securityDao.findRoleByName(TestObjectContainer.ROLE_Y.getRoleName())).thenReturn(TestObjectContainer.ROLE_Y);
		resultRole = securityDomainServices.findRoleByName(TestObjectContainer.ROLE_Y.getRoleName());
		assertEquals(TestObjectContainer.ROLE_Y, resultRole);
		
		String otherName = "otherName";
		resultRole = securityDomainServices.findRoleByName(otherName);
		assertTrue(resultRole == null);

		// test with arbitrary domain-objects:
		final String ROLE_1_ID = UUID.randomUUID().toString();
		final String ROLE_1_NAME = "role1Name";
		IRole role1 = new MyRoleImpl(ROLE_1_ID, ROLE_1_NAME);
		
		Mockito.when(securityDao.findRoleByName(ROLE_1_ID)).thenReturn(role1);
		
		resultRole = securityDomainServices.findRoleByName(ROLE_1_ID);
		assertEquals(ROLE_1_NAME, resultRole.getRoleName());
		assertEquals(ROLE_1_ID, resultRole.getIdentifier());

		final String ROLE_2_ID = UUID.randomUUID().toString();
		final String ROLE_2_NAME = "role2Name";
		IRole role2 = new MyRoleImpl(ROLE_2_ID, ROLE_2_NAME);
		
		Mockito.when(securityDao.findRoleByName(ROLE_2_ID)).thenReturn(role2);
		
		resultRole = securityDomainServices.findRoleByName(ROLE_2_ID);
		assertEquals(ROLE_2_NAME, resultRole.getRoleName());
		assertEquals(ROLE_2_ID, resultRole.getIdentifier());

		resultRole = securityDomainServices.findRoleByName(otherName);
		assertTrue(resultRole == null);
	}

	@Test
	public void testFindPermissionById()
	{
		// test with open-ur-specific domain-objects:
		Mockito.when(securityDao.findPermissionById(TestObjectContainer.PERMISSION_1_A.getIdentifier())).thenReturn(TestObjectContainer.PERMISSION_1_A);
		Mockito.when(securityDao.findPermissionById(TestObjectContainer.PERMISSION_1_B.getIdentifier())).thenReturn(TestObjectContainer.PERMISSION_1_B);
		
		IPermission resultPermission = securityDomainServices.findPermissionById(TestObjectContainer.PERMISSION_1_A.getIdentifier());
		assertEquals(TestObjectContainer.PERMISSION_1_A, resultPermission);
		
		resultPermission = securityDomainServices.findPermissionById(TestObjectContainer.PERMISSION_1_B.getIdentifier());
		assertEquals(TestObjectContainer.PERMISSION_1_B, resultPermission);
		
		String otherId = UUID.randomUUID().toString();
		resultPermission = securityDomainServices.findPermissionById(otherId);
		assertTrue(resultPermission == null);

		// test with arbitrary domain-objects:
		final String APP_ID = UUID.randomUUID().toString();
		final String APP_NAME = "appName";
		IApplication app = new MyApplicationImpl(APP_ID, APP_NAME);
		
		final String PERM_ID_1 = UUID.randomUUID().toString();
		final String PERM_1_NAME = "perm1Name";
		IPermission perm1 = new MyPermissionImpl(PERM_ID_1, PERM_1_NAME, app);
		
		final String PERM_ID_2 = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_ID_2, PERM_2_NAME, app);
		
		Mockito.when(securityDao.findPermissionById(PERM_ID_1)).thenReturn(perm1);
		Mockito.when(securityDao.findPermissionById(PERM_ID_2)).thenReturn(perm2);
		
		resultPermission = securityDomainServices.findPermissionById(PERM_ID_1);
		assertEquals(resultPermission.getIdentifier(), perm1.getIdentifier());
		assertEquals(resultPermission.getPermissionText(), perm1.getPermissionText());
		
		resultPermission = securityDomainServices.findPermissionById(PERM_ID_2);
		assertEquals(resultPermission.getIdentifier(), perm2.getIdentifier());
		assertEquals(resultPermission.getPermissionText(), perm2.getPermissionText());
		
		otherId = UUID.randomUUID().toString();
		resultPermission = securityDomainServices.findPermissionById(otherId);
		assertTrue(resultPermission == null);
	}

	@Test
	public void testFindPermission()
	{
		// test with open-ur-specific domain-objects:
		Mockito.when(securityDao.findPermission(TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName()))
			.thenReturn(TestObjectContainer.PERMISSION_1_A);
		Mockito.when(securityDao.findPermission(TestObjectContainer.PERMISSION_1_B.getPermissionText(), TestObjectContainer.APP_B.getApplicationName()))
			.thenReturn(TestObjectContainer.PERMISSION_1_B);
		
		IPermission resultPermission = securityDomainServices.findPermission(
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName());
		assertEquals(TestObjectContainer.PERMISSION_1_A, resultPermission);
		
		resultPermission = securityDomainServices.findPermission(
				TestObjectContainer.PERMISSION_1_B.getPermissionText(), TestObjectContainer.APP_B.getApplicationName());
		assertEquals(TestObjectContainer.PERMISSION_1_B, resultPermission);

		resultPermission = securityDomainServices.findPermission(TestObjectContainer.PERMISSION_1_A.getPermissionText(), "someApplicationName");
		assertNull(resultPermission);
		resultPermission = securityDomainServices.findPermission(TestObjectContainer.PERMISSION_1_B.getPermissionText(), "someApplicationName");
		assertNull(resultPermission);
		resultPermission = securityDomainServices.findPermission("somePermissionText", TestObjectContainer.APP_A.getApplicationName());
		assertNull(resultPermission);
		resultPermission = securityDomainServices.findPermission("somePermissionText", TestObjectContainer.APP_B.getApplicationName());
		assertNull(resultPermission);

		// test with arbitrary domain-objects:
		final String APP_ID = UUID.randomUUID().toString();
		final String APP_NAME = "appName";
		IApplication app = new MyApplicationImpl(APP_ID, APP_NAME);
		
		final String PERM_ID_1 = UUID.randomUUID().toString();
		final String PERM_1_NAME = "perm1Name";
		IPermission perm1 = new MyPermissionImpl(PERM_ID_1, PERM_1_NAME, app);
		
		final String PERM_ID_2 = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_ID_2, PERM_2_NAME, app);
		
		Mockito.when(securityDao.findPermission(PERM_1_NAME, APP_NAME)).thenReturn(perm1);
		Mockito.when(securityDao.findPermission(PERM_2_NAME, APP_NAME)).thenReturn(perm2);
		
		resultPermission = securityDomainServices.findPermission(PERM_1_NAME, APP_NAME);
		assertEquals(resultPermission.getPermissionText(), perm1.getPermissionText());
		assertEquals(resultPermission.getIdentifier(), perm1.getIdentifier());
		
		resultPermission = securityDomainServices.findPermission(PERM_2_NAME, APP_NAME);
		assertEquals(resultPermission.getPermissionText(), perm2.getPermissionText());
		assertEquals(resultPermission.getIdentifier(), perm2.getIdentifier());
		
		resultPermission = securityDomainServices.findPermission(PERM_1_NAME, "someAppName");
		assertNull(resultPermission);
		resultPermission = securityDomainServices.findPermission(PERM_2_NAME, "someAppName");
		assertNull(resultPermission);
		resultPermission = securityDomainServices.findPermission("somePermText", APP_NAME);
		assertNull(resultPermission);
	}

	@Test
	public void testObtainAllPermissions()
	{		
		// test with open-ur-specific domain-objects:
		Mockito.when(securityDao.obtainAllPermissions()).thenReturn(Arrays.asList(TestObjectContainer.PERMISSION_1_A, TestObjectContainer.PERMISSION_1_B));
		
		Set<IPermission> resultSet = securityDomainServices.obtainAllPermissions();
		
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(TestObjectContainer.PERMISSION_1_A));
		assertTrue(resultSet.contains(TestObjectContainer.PERMISSION_1_B));
		assertFalse(resultSet.contains(TestObjectContainer.PERMISSION_2_A));
		assertFalse(resultSet.contains(TestObjectContainer.PERMISSION_2_B));
		assertFalse(resultSet.contains(TestObjectContainer.PERMISSION_1_C));
		assertFalse(resultSet.contains(TestObjectContainer.PERMISSION_2_C));

		// test with arbitrary domain-objects:
		final String APP_ID = UUID.randomUUID().toString();
		final String APP_NAME = "appName";
		IApplication app = new MyApplicationImpl(APP_ID, APP_NAME);
		
		final String PERM_ID_1 = UUID.randomUUID().toString();
		final String PERM_1_NAME = "perm1Name";
		IPermission perm1 = new MyPermissionImpl(PERM_ID_1, PERM_1_NAME, app);
		
		final String PERM_ID_2 = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_ID_2, PERM_2_NAME, app);
		
		Mockito.when(securityDao.obtainAllPermissions()).thenReturn(Arrays.asList(perm1, perm2));
		
		resultSet = securityDomainServices.obtainAllPermissions();
		
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		
		for (IPermission p : resultSet)
		{
			assertTrue(PERM_ID_1.equals(p.getIdentifier()) || PERM_ID_2.equals(p.getIdentifier()));
			assertTrue(PERM_1_NAME.equals(p.getPermissionText()) || PERM_2_NAME.equals(p.getPermissionText()));
		}
		
		final String OTHER_ID = UUID.randomUUID().toString();
		
		for (IPermission p : resultSet)
		{
			assertFalse(OTHER_ID.equals(p.getIdentifier()));
		}
	}

	@Test
	public void testObtainPermissionsForApp()
	{
		// test with open-ur-specific domain-objects:
		Mockito.when(securityDao.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName()))
				.thenReturn(Arrays.asList(TestObjectContainer.PERMISSION_1_A, TestObjectContainer.PERMISSION_2_A));
		
		Set<IPermission> resultSet = securityDomainServices.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName());
		
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(TestObjectContainer.PERMISSION_1_A));
		assertTrue(resultSet.contains(TestObjectContainer.PERMISSION_2_A));
		assertFalse(resultSet.contains(TestObjectContainer.PERMISSION_1_B));
		assertFalse(resultSet.contains(TestObjectContainer.PERMISSION_2_B));
		assertFalse(resultSet.contains(TestObjectContainer.PERMISSION_1_C));
		assertFalse(resultSet.contains(TestObjectContainer.PERMISSION_2_C));

		// test with arbitrary domain-objects:
		final String APP_1_ID = UUID.randomUUID().toString();
		final String APP_1_NAME = "app1Name";
		IApplication app1 = new MyApplicationImpl(APP_1_ID, APP_1_NAME);
		
		final String PERM_11_ID = UUID.randomUUID().toString();
		final String PERM_11_NAME = "perm11Name";
		IPermission perm11 = new MyPermissionImpl(PERM_11_ID, PERM_11_NAME, app1);
		
		final String PERM_12_ID = UUID.randomUUID().toString();
		final String PERM_12_NAME = "perm12Name";
		IPermission perm12 = new MyPermissionImpl(PERM_12_ID, PERM_12_NAME, app1);

		Mockito.when(securityDao.obtainPermissionsForApp(app1.getApplicationName())).thenReturn(Arrays.asList(perm11, perm12));

		final String APP_2_ID = UUID.randomUUID().toString();
		final String APP_2_NAME = "app2Name";
		IApplication app2 = new MyApplicationImpl(APP_2_ID, APP_2_NAME);
		
		final String PERM_21_ID = UUID.randomUUID().toString();
		final String PERM_21_NAME = "perm21Name";
		IPermission perm21 = new MyPermissionImpl(PERM_21_ID, PERM_21_NAME, app2);
		
		final String PERM_22_ID = UUID.randomUUID().toString();
		final String PERM_22_NAME = "perm22Name";
		IPermission perm22 = new MyPermissionImpl(PERM_22_ID, PERM_22_NAME, app2);

		Mockito.when(securityDao.obtainPermissionsForApp(app2.getApplicationName())).thenReturn(Arrays.asList(perm21, perm22));
		
		resultSet = securityDomainServices.obtainPermissionsForApp(app1.getApplicationName());
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		
		for (IPermission p : resultSet)
		{
			assertTrue(PERM_11_ID.equals(p.getIdentifier()) || PERM_12_ID.equals(p.getIdentifier()));
			assertFalse(PERM_21_ID.equals(p.getIdentifier()) || PERM_22_ID.equals(p.getIdentifier()));
		}
		
		resultSet = securityDomainServices.obtainPermissionsForApp(app2.getApplicationName());
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		
		for (IPermission p : resultSet)
		{
			assertTrue(PERM_21_ID.equals(p.getIdentifier()) || PERM_22_ID.equals(p.getIdentifier()));
			assertFalse(PERM_11_ID.equals(p.getIdentifier()) || PERM_12_ID.equals(p.getIdentifier()));
		}
	}
}
