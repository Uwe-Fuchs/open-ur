package org.openur.module.service.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
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
		OpenURRole role1 = new OpenURRoleBuilder("role1").build();
		OpenURRole role2 = new OpenURRoleBuilder("role2").build();		
		Mockito.when(securityDao.obtainAllRoles()).thenReturn(Arrays.asList(role1, role2));
		
		Set<IRole> resultSet = securityDomainServices.obtainAllRoles();
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
		OpenURRole role1 = new OpenURRoleBuilder(ROLE_ID_1, "role1").build();
		OpenURRole role2 = new OpenURRoleBuilder(ROLE_ID_2, "role2").build();	
		Mockito.when(securityDao.findRoleById(ROLE_ID_1)).thenReturn(role1);
		Mockito.when(securityDao.findRoleById(ROLE_ID_2)).thenReturn(role2);
		
		IRole resultRole = securityDomainServices.findRoleById(ROLE_ID_1);
		assertEquals(resultRole, role1);
		resultRole = securityDomainServices.findRoleById(ROLE_ID_2);
		assertEquals(resultRole, role2);
		resultRole = securityDomainServices.findRoleById("abcdef");
		assertEquals(resultRole, null);
	}

	@Test
	public void testFindRoleByName()
	{
		final String ROLE_NAME_1 = "role1";
		final String ROLE_NAME_2 = "role2";
		OpenURRole role1 = new OpenURRoleBuilder(ROLE_NAME_1).build();
		OpenURRole role2 = new OpenURRoleBuilder(ROLE_NAME_2).build();
		Mockito.when(securityDao.findRoleByName(ROLE_NAME_1)).thenReturn(role1);
		Mockito.when(securityDao.findRoleByName(ROLE_NAME_2)).thenReturn(role2);
		
		IRole resultRole = securityDomainServices.findRoleByName(ROLE_NAME_1);
		assertEquals(resultRole, role1);
		resultRole = securityDomainServices.findRoleByName(ROLE_NAME_2);
		assertEquals(resultRole, role2);
		resultRole = securityDomainServices.findRoleByName("abcdef");
		assertEquals(resultRole, null);	
	}

	@Test
	public void testFindPermissionById()
	{
		OpenURApplication app = new OpenURApplicationBuilder("app").build();		
		final String PERM_ID_1 = "111aaa";
		final String PERM_ID_2 = "222bbb";
		OpenURPermission perm1 = new OpenURPermissionBuilder(
			PERM_ID_1, "perm1", PermissionScope.SELECTED, app).build();		
		OpenURPermission perm2 = new OpenURPermissionBuilder(
			PERM_ID_2, "perm2", PermissionScope.SELECTED, app).build();
		
		Mockito.when(securityDao.findPermissionById(PERM_ID_1)).thenReturn(perm1);
		Mockito.when(securityDao.findPermissionById(PERM_ID_2)).thenReturn(perm2);
		
		IPermission resultPermission = securityDomainServices.findPermissionById(PERM_ID_1);
		assertEquals(resultPermission, perm1);
		resultPermission = securityDomainServices.findPermissionById(PERM_ID_2);
		assertEquals(resultPermission, perm2);
		resultPermission = securityDomainServices.findPermissionById("abcdef");
		assertEquals(resultPermission, null);
	}

	@Test
	public void testFindPermissionByName()
	{
		OpenURApplication app = new OpenURApplicationBuilder("app").build();		
		final String PERM_NAME_1 = "perm1";
		final String PERM_NAME_2 = "perm2";
		OpenURPermission perm1 = new OpenURPermissionBuilder(
			PERM_NAME_1, PermissionScope.SELECTED, app).build();		
		OpenURPermission perm2 = new OpenURPermissionBuilder(
			PERM_NAME_2, PermissionScope.SELECTED, app).build();
		
		Mockito.when(securityDao.findPermissionByName(PERM_NAME_1, app)).thenReturn(perm1);
		Mockito.when(securityDao.findPermissionByName(PERM_NAME_2, app)).thenReturn(perm2);
		
		IPermission resultPermission 
			= securityDomainServices.findPermissionByName(PERM_NAME_1, app);
		assertEquals(resultPermission, perm1);
		resultPermission = securityDomainServices.findPermissionByName(PERM_NAME_2, app);
		assertEquals(resultPermission, perm2);
		resultPermission = securityDomainServices.findPermissionByName("abcdef", app);
		assertEquals(resultPermission, null);
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2").build();	
		resultPermission = securityDomainServices.findPermissionByName(PERM_NAME_1, app2);
		assertEquals(resultPermission, null);
	}

	@Test
	public void testObtainAllPermissions()
	{
		OpenURApplication app = new OpenURApplicationBuilder("app").build();
		OpenURPermission perm1 = new OpenURPermissionBuilder(
			"perm1", PermissionScope.SELECTED, app).build();		
		OpenURPermission perm2 = new OpenURPermissionBuilder(
			"perm2", PermissionScope.SELECTED, app).build();
		Mockito.when(securityDao.obtainAllPermissions()).thenReturn(Arrays.asList(perm1, perm2));
		
		Set<IPermission> resultSet = securityDomainServices.obtainAllPermissions();
		assertTrue(CollectionUtils.isNotEmpty(resultSet));
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(perm1));
		assertTrue(resultSet.contains(perm2));
	}

	@Test
	public void testObtainPermissionsForApp()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1").build();
		OpenURPermission perm11 = new OpenURPermissionBuilder(
			"perm11", PermissionScope.SELECTED, app1).build();		
		OpenURPermission perm12 = new OpenURPermissionBuilder(
			"perm12", PermissionScope.SELECTED, app1).build();
		Mockito.when(securityDao.obtainPermissionsForApp(app1))
			.thenReturn(Arrays.asList(perm11, perm12));
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2").build();
		OpenURPermission perm21 = new OpenURPermissionBuilder(
			"perm21", PermissionScope.SELECTED, app2).build();		
		OpenURPermission perm22 = new OpenURPermissionBuilder(
			"perm22", PermissionScope.SELECTED, app2).build();
		Mockito.when(securityDao.obtainPermissionsForApp(app2))
			.thenReturn(Arrays.asList(perm21, perm22));
		
		Set<IPermission> resultSet = securityDomainServices.obtainPermissionsForApp(app1);
		assertTrue(CollectionUtils.isNotEmpty(resultSet));
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(perm11));
		assertTrue(resultSet.contains(perm12));
		assertFalse(resultSet.contains(perm21));
		assertFalse(resultSet.contains(perm22));
		
		resultSet = securityDomainServices.obtainPermissionsForApp(app2);
		assertTrue(CollectionUtils.isNotEmpty(resultSet));
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(perm21));
		assertTrue(resultSet.contains(perm22));
		assertFalse(resultSet.contains(perm11));
		assertFalse(resultSet.contains(perm12));
	}

	@Test
	public void testFindAuthOrgUnitById()
	{		
		final String UUID_1 = UUID.randomUUID().toString();
		final String UUID_2 = UUID.randomUUID().toString();
		
		AuthorizableOrgUnit orgUnit1 = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder(UUID_1)
			.name("Human Resources")
			.build();
		
		AuthorizableOrgUnit orgUnit2 = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder(UUID_2)
			.name("Marketing")
			.build();
		
		Mockito.when(securityDao.findAuthOrgUnitById(UUID_1)).thenReturn(orgUnit1);
		Mockito.when(securityDao.findAuthOrgUnitById(UUID_2)).thenReturn(orgUnit2);	
		
		IAuthorizableOrgUnit p = securityDomainServices.findAuthOrgUnitById(UUID_1);		
		assertNotNull(p);
		assertEquals("Name", "Human Resources", ((OrganizationalUnit) p).getName());	
		
		IAuthorizableOrgUnit p2 = securityDomainServices.findAuthOrgUnitById(UUID_2);		
		assertNotNull(p2);
		assertEquals("Name", "Marketing", ((OrganizationalUnit) p2).getName());
	}
}
