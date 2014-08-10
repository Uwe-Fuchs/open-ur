package org.openur.module.service.security;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.service.security.ISecurityDomainServices;
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
		IRole role1 = new OpenURRoleBuilder("role1").build();
		IRole role2 = new OpenURRoleBuilder("role2").build();		
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
		IRole role1 = new OpenURRoleBuilder(ROLE_ID_1, "role1").build();
		IRole role2 = new OpenURRoleBuilder(ROLE_ID_2, "role2").build();	
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
		IRole role1 = new OpenURRoleBuilder(ROLE_NAME_1).build();
		IRole role2 = new OpenURRoleBuilder(ROLE_NAME_2).build();
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
		IApplication app = new OpenURApplicationBuilder("app", "user1", "pw").build();		
		final String PERM_ID_1 = "111aaa";
		final String PERM_ID_2 = "222bbb";
		IPermission perm1 = new OpenURPermissionBuilder(
			PERM_ID_1, "perm1", PermissionScope.SELECTED, app).build();		
		IPermission perm2 = new OpenURPermissionBuilder(
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
			= securityDomainServices.findPermissionByName(PERM_NAME_1, app);
		assertEquals(resultPermission, perm1);
		resultPermission = securityDomainServices.findPermissionByName(PERM_NAME_2, app);
		assertEquals(resultPermission, perm2);
		resultPermission = securityDomainServices.findPermissionByName("abcdef", app);
		assertEquals(resultPermission, null);
		
		IApplication app2 = new OpenURApplicationBuilder("app2", "user2", "pw2").build();	
		resultPermission = securityDomainServices.findPermissionByName(PERM_NAME_1, app2);
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
		
		Set<IPermission> resultSet = securityDomainServices.obtainAllPermissions();
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
		
		IAuthorizableOrgUnit orgUnit1 = new AuthorizableOrgUnit(
			new OrganizationalUnitBuilder(UUID_1).name("Human Resources"));
		IAuthorizableOrgUnit orgUnit2 = new AuthorizableOrgUnit(
			new OrganizationalUnitBuilder(UUID_2).name("Marketing"));
		
		Mockito.when(securityDao.findAuthOrgUnitById(UUID_1)).thenReturn(orgUnit1);
		Mockito.when(securityDao.findAuthOrgUnitById(UUID_2)).thenReturn(orgUnit2);	
		
		IOrganizationalUnit p = securityDomainServices.findAuthOrgUnitById(UUID_1);		
		assertNotNull(p);
		assertEquals("Name", "Human Resources", ((OrganizationalUnit) p).getName());	
		
		IOrganizationalUnit p2 = securityDomainServices.findAuthOrgUnitById(UUID_2);		
		assertNotNull(p2);
		assertEquals("Name", "Marketing", ((OrganizationalUnit) p2).getName());
	}
}
