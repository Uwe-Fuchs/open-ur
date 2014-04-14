package org.openur.module.service.security;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.security.OpenURApplicationBuilder;
import org.openur.module.domain.security.OpenURPermissionBuilder;
import org.openur.module.domain.security.OpenURRoleBuilder;
import org.openur.module.domain.security.PermissionScope;
import org.openur.module.persistence.security.ISecurityDao;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class SecurityRelatedUserServicesTest
{
	@Inject
	private ISecurityDao securityDao;
	
	@Inject
	private ISecurityRelatedUserServices securityRelatedUserServices;

	@Test
	public void testObtainAllRoles()
	{
		IRole role1 = new OpenURRoleBuilder("role1").build();
		IRole role2 = new OpenURRoleBuilder("role2").build();		
		Mockito.when(securityDao.obtainAllRoles()).thenReturn(Arrays.asList(role1, role2));
		
		Set<IRole> resultSet = securityRelatedUserServices.obtainAllRoles();
		assertTrue(CollectionUtils.isNotEmpty(resultSet));
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(role1));
		assertTrue(resultSet.contains(role2));
	}

	@Test
	public void testFindRoleById()
	{
		final String ROLE_ID_1 = "111aaa";
		final String ROLE_ID_2 = "222bbb";
		IRole role1 = new OpenURRoleBuilder(ROLE_ID_1, "role1").build();
		IRole role2 = new OpenURRoleBuilder(ROLE_ID_2, "role2").build();	
		Mockito.when(securityDao.findRoleById(ROLE_ID_1)).thenReturn(role1);
		Mockito.when(securityDao.findRoleById(ROLE_ID_2)).thenReturn(role2);
		
		IRole resultRole = securityRelatedUserServices.findRoleById(ROLE_ID_1);
		assertEquals(resultRole, role1);
		resultRole = securityRelatedUserServices.findRoleById(ROLE_ID_2);
		assertEquals(resultRole, role2);
		resultRole = securityRelatedUserServices.findRoleById("abcdef");
		assertEquals(resultRole, null);
	}

	@Test
	public void testFindRoleByName()
	{
		final String ROLE_NAME_1 = "role1";
		final String ROLE_NAME_2 = "role2";
		IRole role1 = new OpenURRoleBuilder(ROLE_NAME_1).build();
		IRole role2 = new OpenURRoleBuilder(ROLE_NAME_2).build();
		Mockito.when(securityDao.findRoleByName(ROLE_NAME_1)).thenReturn(role1);
		Mockito.when(securityDao.findRoleByName(ROLE_NAME_2)).thenReturn(role2);
		
		IRole resultRole = securityRelatedUserServices.findRoleByName(ROLE_NAME_1);
		assertEquals(resultRole, role1);
		resultRole = securityRelatedUserServices.findRoleByName(ROLE_NAME_2);
		assertEquals(resultRole, role2);
		resultRole = securityRelatedUserServices.findRoleByName("abcdef");
		assertEquals(resultRole, null);	
	}

	@Test
	public void testFindPermissionById()
	{
		IApplication app = new OpenURApplicationBuilder("app", "user1", "pw").build();		
		final String PERM_ID_1 = "111aaa";
		final String PERM_ID_2 = "222bbb";
		IPermission perm1 = new OpenURPermissionBuilder(
			PERM_ID_1, "perm1", PermissionScope.SELECTED, app).build();		
		IPermission perm2 = new OpenURPermissionBuilder(
			PERM_ID_2, "perm2", PermissionScope.SELECTED, app).build();
		
		Mockito.when(securityDao.findPermissionById(PERM_ID_1)).thenReturn(perm1);
		Mockito.when(securityDao.findPermissionById(PERM_ID_2)).thenReturn(perm2);
		
		IPermission resultPermission = securityRelatedUserServices.findPermissionById(PERM_ID_1);
		assertEquals(resultPermission, perm1);
		resultPermission = securityRelatedUserServices.findPermissionById(PERM_ID_2);
		assertEquals(resultPermission, perm2);
		resultPermission = securityRelatedUserServices.findPermissionById("abcdef");
		assertEquals(resultPermission, null);
	}

	@Test
	public void testFindPermissionByName()
	{
		IApplication app = new OpenURApplicationBuilder("app", "user1", "pw").build();		
		final String PERM_NAME_1 = "perm1";
		final String PERM_NAME_2 = "perm2";
		IPermission perm1 = new OpenURPermissionBuilder(
			PERM_NAME_1, PermissionScope.SELECTED, app).build();		
		IPermission perm2 = new OpenURPermissionBuilder(
			PERM_NAME_2, PermissionScope.SELECTED, app).build();
		
		Mockito.when(securityDao.findPermissionByName(PERM_NAME_1, app)).thenReturn(perm1);
		Mockito.when(securityDao.findPermissionByName(PERM_NAME_2, app)).thenReturn(perm2);
		
		IPermission resultPermission 
			= securityRelatedUserServices.findPermissionByName(PERM_NAME_1, app);
		assertEquals(resultPermission, perm1);
		resultPermission = securityRelatedUserServices.findPermissionByName(PERM_NAME_2, app);
		assertEquals(resultPermission, perm2);
		resultPermission = securityRelatedUserServices.findPermissionByName("abcdef", app);
		assertEquals(resultPermission, null);
		
		IApplication app2 = new OpenURApplicationBuilder("app2", "user2", "pw2").build();	
		resultPermission = securityRelatedUserServices.findPermissionByName(PERM_NAME_1, app2);
		assertEquals(resultPermission, null);
	}

	@Test
	public void testObtainAllPermissions()
	{
		IApplication app = new OpenURApplicationBuilder("app", "user1", "pw").build();
		IPermission perm1 = new OpenURPermissionBuilder(
			"perm1", PermissionScope.SELECTED, app).build();		
		IPermission perm2 = new OpenURPermissionBuilder(
			"perm2", PermissionScope.SELECTED, app).build();
		Mockito.when(securityDao.obtainAllPermissions()).thenReturn(Arrays.asList(perm1, perm2));
		
		Set<IPermission> resultSet = securityRelatedUserServices.obtainAllPermissions();
		assertTrue(CollectionUtils.isNotEmpty(resultSet));
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(perm1));
		assertTrue(resultSet.contains(perm2));
	}

	@Test
	public void testObtainPermissionsForApp()
	{
		IApplication app1 = new OpenURApplicationBuilder("app1", "user1", "pw1").build();
		IPermission perm11 = new OpenURPermissionBuilder(
			"perm11", PermissionScope.SELECTED, app1).build();		
		IPermission perm12 = new OpenURPermissionBuilder(
			"perm12", PermissionScope.SELECTED, app1).build();
		Mockito.when(securityDao.obtainPermissionsForApp(app1))
			.thenReturn(Arrays.asList(perm11, perm12));
		
		IApplication app2 = new OpenURApplicationBuilder("app2", "user2", "pw2").build();
		IPermission perm21 = new OpenURPermissionBuilder(
			"perm21", PermissionScope.SELECTED, app2).build();		
		IPermission perm22 = new OpenURPermissionBuilder(
			"perm22", PermissionScope.SELECTED, app2).build();
		Mockito.when(securityDao.obtainPermissionsForApp(app2))
			.thenReturn(Arrays.asList(perm21, perm22));
		
		Set<IPermission> resultSet = securityRelatedUserServices.obtainPermissionsForApp(app1);
		assertTrue(CollectionUtils.isNotEmpty(resultSet));
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(perm11));
		assertTrue(resultSet.contains(perm12));
		assertFalse(resultSet.contains(perm21));
		assertFalse(resultSet.contains(perm22));
		
		resultSet = securityRelatedUserServices.obtainPermissionsForApp(app2);
		assertTrue(CollectionUtils.isNotEmpty(resultSet));
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(perm21));
		assertTrue(resultSet.contains(perm22));
		assertFalse(resultSet.contains(perm11));
		assertFalse(resultSet.contains(perm12));
	}
}
