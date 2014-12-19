package org.openur.module.service.security;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.persistence.dao.ISecurityDao;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class SecurityDomainTest
{
	@Inject
	private ISecurityDao securityDao;
	
	@Inject
	private ISecurityDomainServices securityDomainServices;

	@Test
	public void testObtainAllRoles()
	{
		final String ROLE_1_ID = UUID.randomUUID().toString();
		final String ROLE_1_NAME = "role1Name";
		IRole role1 = new MyRoleImpl(ROLE_1_ID, ROLE_1_NAME);
		
		final String ROLE_2_ID = UUID.randomUUID().toString();
		final String ROLE_2_NAME = "role2Name";
		IRole role2 = new MyRoleImpl(ROLE_2_ID, ROLE_2_NAME);

		Mockito.when(securityDao.obtainAllRoles()).thenReturn(Arrays.asList(role1, role2));
		
		Set<IRole> resultSet = securityDomainServices.obtainAllRoles();
		
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
		final String ROLE_1_ID = UUID.randomUUID().toString();
		final String ROLE_1_NAME = "role1Name";
		IRole role1 = new MyRoleImpl(ROLE_1_ID, ROLE_1_NAME);
		
		Mockito.when(securityDao.findRoleById(ROLE_1_ID)).thenReturn(role1);
		
		IRole resultRole = securityDomainServices.findRoleById(ROLE_1_ID);
		assertEquals(ROLE_1_ID, resultRole.getIdentifier());
		assertEquals(ROLE_1_NAME, resultRole.getRoleName());

		final String ROLE_2_ID = UUID.randomUUID().toString();
		final String ROLE_2_NAME = "role2Name";
		IRole role2 = new MyRoleImpl(ROLE_2_ID, ROLE_2_NAME);
		
		Mockito.when(securityDao.findRoleById(ROLE_2_ID)).thenReturn(role2);
		
		resultRole = securityDomainServices.findRoleById(ROLE_2_ID);
		assertEquals(ROLE_2_ID, resultRole.getIdentifier());
		assertEquals(ROLE_2_NAME, resultRole.getRoleName());
		
		final String OTHER_ID = UUID.randomUUID().toString();
		resultRole = securityDomainServices.findRoleById(OTHER_ID);
		assertTrue(resultRole == null || !ROLE_1_ID.equals(resultRole.getIdentifier()) || !ROLE_2_ID.equals(resultRole.getIdentifier()));
	}

	@Test
	public void testFindRoleByName()
	{
		final String ROLE_1_ID = UUID.randomUUID().toString();
		final String ROLE_1_NAME = "role1Name";
		IRole role1 = new MyRoleImpl(ROLE_1_ID, ROLE_1_NAME);
		
		Mockito.when(securityDao.findRoleByName(ROLE_1_ID)).thenReturn(role1);
		
		IRole resultRole = securityDomainServices.findRoleByName(ROLE_1_ID);
		assertEquals(ROLE_1_NAME, resultRole.getRoleName());
		assertEquals(ROLE_1_ID, resultRole.getIdentifier());

		final String ROLE_2_ID = UUID.randomUUID().toString();
		final String ROLE_2_NAME = "role2Name";
		IRole role2 = new MyRoleImpl(ROLE_2_ID, ROLE_2_NAME);
		
		Mockito.when(securityDao.findRoleByName(ROLE_2_ID)).thenReturn(role2);
		
		resultRole = securityDomainServices.findRoleByName(ROLE_2_ID);
		assertEquals(ROLE_2_NAME, resultRole.getRoleName());
		assertEquals(ROLE_2_ID, resultRole.getIdentifier());
		
		final String OTHER_NAME = "otherName";
		resultRole = securityDomainServices.findRoleByName(OTHER_NAME);
		assertTrue(resultRole == null || !OTHER_NAME.equals(resultRole.getRoleName()) || !OTHER_NAME.equals(resultRole.getRoleName()));
	}

	@Test
	public void testFindPermissionById()
	{
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
		
		IPermission resultPermission = securityDomainServices.findPermissionById(PERM_ID_1);
		assertEquals(resultPermission.getIdentifier(), perm1.getIdentifier());
		assertEquals(resultPermission.getPermissionName(), perm1.getPermissionName());
		
		resultPermission = securityDomainServices.findPermissionById(PERM_ID_2);
		assertEquals(resultPermission.getIdentifier(), perm2.getIdentifier());
		assertEquals(resultPermission.getPermissionName(), perm2.getPermissionName());
		
		final String OTHER_ID = UUID.randomUUID().toString();
		resultPermission = securityDomainServices.findPermissionById(OTHER_ID);
		assertTrue(resultPermission == null || !PERM_ID_1.equals(resultPermission.getIdentifier()) || !!PERM_ID_2.equals(resultPermission.getIdentifier()));
	}

	@Test
	public void testFindPermissionByName()
	{
		final String APP_ID = UUID.randomUUID().toString();
		final String APP_NAME = "appName";
		IApplication app = new MyApplicationImpl(APP_ID, APP_NAME);
		
		final String PERM_ID_1 = UUID.randomUUID().toString();
		final String PERM_1_NAME = "perm1Name";
		IPermission perm1 = new MyPermissionImpl(PERM_ID_1, PERM_1_NAME, app);
		
		final String PERM_ID_2 = UUID.randomUUID().toString();
		final String PERM_2_NAME = "perm2Name";
		IPermission perm2 = new MyPermissionImpl(PERM_ID_2, PERM_2_NAME, app);
		
		Mockito.when(securityDao.findPermissionByName(PERM_1_NAME, app)).thenReturn(perm1);
		Mockito.when(securityDao.findPermissionByName(PERM_2_NAME, app)).thenReturn(perm2);
		
		IPermission resultPermission = securityDomainServices.findPermissionByName(PERM_1_NAME, app);
		assertEquals(resultPermission.getPermissionName(), perm1.getPermissionName());
		assertEquals(resultPermission.getIdentifier(), perm1.getIdentifier());
		
		resultPermission = securityDomainServices.findPermissionByName(PERM_2_NAME, app);
		assertEquals(resultPermission.getPermissionName(), perm2.getPermissionName());
		assertEquals(resultPermission.getIdentifier(), perm2.getIdentifier());
		
		resultPermission = securityDomainServices.findPermissionByName("abcdef", app);
		assertEquals(resultPermission, null);
		
		final String OTHER_NAME = "otherName";
		resultPermission = securityDomainServices.findPermissionByName(OTHER_NAME, app);
		assertTrue(resultPermission == null || !PERM_ID_1.equals(resultPermission.getPermissionName()) || !!PERM_ID_2.equals(resultPermission.getPermissionName()));
	}

	@Test
	public void testObtainAllPermissions()
	{
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
		
		Set<IPermission> resultSet = securityDomainServices.obtainAllPermissions();
		
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		
		for (IPermission p : resultSet)
		{
			assertTrue(PERM_ID_1.equals(p.getIdentifier()) || PERM_ID_2.equals(p.getIdentifier()));
			assertTrue(PERM_1_NAME.equals(p.getPermissionName()) || PERM_2_NAME.equals(p.getPermissionName()));
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
		final String APP_1_ID = UUID.randomUUID().toString();
		final String APP_1_NAME = "app1Name";
		IApplication app1 = new MyApplicationImpl(APP_1_ID, APP_1_NAME);
		
		final String PERM_11_ID = UUID.randomUUID().toString();
		final String PERM_11_NAME = "perm11Name";
		IPermission perm11 = new MyPermissionImpl(PERM_11_ID, PERM_11_NAME, app1);
		
		final String PERM_12_ID = UUID.randomUUID().toString();
		final String PERM_12_NAME = "perm12Name";
		IPermission perm12 = new MyPermissionImpl(PERM_12_ID, PERM_12_NAME, app1);

		Mockito.when(securityDao.obtainPermissionsForApp(app1)).thenReturn(Arrays.asList(perm11, perm12));

		final String APP_2_ID = UUID.randomUUID().toString();
		final String APP_2_NAME = "app2Name";
		IApplication app2 = new MyApplicationImpl(APP_2_ID, APP_2_NAME);
		
		final String PERM_21_ID = UUID.randomUUID().toString();
		final String PERM_21_NAME = "perm21Name";
		IPermission perm21 = new MyPermissionImpl(PERM_21_ID, PERM_21_NAME, app2);
		
		final String PERM_22_ID = UUID.randomUUID().toString();
		final String PERM_22_NAME = "perm22Name";
		IPermission perm22 = new MyPermissionImpl(PERM_22_ID, PERM_22_NAME, app2);

		Mockito.when(securityDao.obtainPermissionsForApp(app2)).thenReturn(Arrays.asList(perm21, perm22));
		
		Set<IPermission> resultSet = securityDomainServices.obtainPermissionsForApp(app1);
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		
		for (IPermission p : resultSet)
		{
			assertTrue(PERM_11_ID.equals(p.getIdentifier()) || PERM_12_ID.equals(p.getIdentifier()));
			assertFalse(PERM_21_ID.equals(p.getIdentifier()) || PERM_22_ID.equals(p.getIdentifier()));
		}
		
		resultSet = securityDomainServices.obtainPermissionsForApp(app2);
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
		
		for (IPermission p : resultSet)
		{
			assertTrue(PERM_21_ID.equals(p.getIdentifier()) || PERM_22_ID.equals(p.getIdentifier()));
			assertFalse(PERM_11_ID.equals(p.getIdentifier()) || PERM_12_ID.equals(p.getIdentifier()));
		}
	}

	@Test
	public void testFindAuthOrgUnitById()
	{		
		final String UUID_1 = UUID.randomUUID().toString();
		final String NAME_1 = "name1";
		IAuthorizableOrgUnit orgUnit1 = new MyAuthorizableOrgUnit(UUID_1, NAME_1);
		
		Mockito.when(securityDao.findAuthOrgUnitById(UUID_1)).thenReturn(orgUnit1);
		
		IAuthorizableOrgUnit o = securityDomainServices.findAuthOrgUnitById(UUID_1);		
		assertNotNull(o);
		assertEquals("identifier", o.getIdentifier(), UUID_1);
		assertEquals("orgunit-number", o.getNumber(), NAME_1);
		
		final String UUID_2 = UUID.randomUUID().toString();
		final String NAME_2 = "name2";
		IAuthorizableOrgUnit orgUnit2 = new MyAuthorizableOrgUnit(UUID_2, NAME_2);
		
		Mockito.when(securityDao.findAuthOrgUnitById(UUID_2)).thenReturn(orgUnit2);
		
		o = securityDomainServices.findAuthOrgUnitById(UUID_1);		
		assertNotNull(o);
		assertEquals("identifier", o.getIdentifier(), UUID_1);
		assertEquals("orgunit-number", o.getNumber(), NAME_1);
		
		final String OTHER_UUID = UUID.randomUUID().toString();
		o = securityDomainServices.findAuthOrgUnitById(OTHER_UUID);
		assertTrue(o == null || !o.getIdentifier().equals(UUID_1) || !o.getIdentifier().equals(UUID_2));
	}
}
