package org.openur.remoting.client.ws.rs.securitydomain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PermissionComparer;
import org.openur.module.domain.utils.compare.RoleComparer;
import org.openur.module.service.securitydomain.ISecurityDomainServices;
import org.openur.remoting.client.ws.rs.securitydomain.SecurityDomainResourceClient;
import org.openur.remoting.resource.securitydomain.SecurityDomainResource;

public class SecurityDomainResourceClientTest
	extends JerseyTest
{
	private ISecurityDomainServices securityDomainServicesMock;
	private SecurityDomainResourceClient securityDomainResourceClient;
	
	@Override
	protected Application configure()
	{
		// mocked service:
		securityDomainServicesMock = Mockito.mock(ISecurityDomainServices.class);
		
		// Http-Testserver:
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(securityDomainServicesMock).to(ISecurityDomainServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(SecurityDomainResource.class)
				.register(binder);
		
		// Client:
		securityDomainResourceClient = new SecurityDomainResourceClient("http://localhost:9998/");		
		
		for (Class<?> provider : ((SecurityDomainResourceClient) securityDomainResourceClient).getProviders())
		{
			config.register(provider);
		}

		return config;
	}

	@Test
	public void testFindRoleById()
	{
		Mockito.when(securityDomainServicesMock.findRoleById(TestObjectContainer.ROLE_X.getIdentifier())).thenReturn(
				TestObjectContainer.ROLE_X);		
		
		IRole r = securityDomainResourceClient.findRoleById(TestObjectContainer.ROLE_X.getIdentifier());

		assertNotNull(r);
		System.out.println("Result: " + r);
		
		assertTrue(new RoleComparer().objectsAreEqual((OpenURRole) r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testFindRoleByName()
	{
		Mockito.when(securityDomainServicesMock.findRoleByName(TestObjectContainer.ROLE_X.getRoleName())).thenReturn(
				TestObjectContainer.ROLE_X);
		
		IRole r = securityDomainResourceClient.findRoleByName(TestObjectContainer.ROLE_X.getRoleName());

		assertNotNull(r);
		System.out.println("Result: " + r);
		
		assertTrue(new RoleComparer().objectsAreEqual((OpenURRole) r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testObtainAllRoles()
	{
		Mockito.when(securityDomainServicesMock.obtainAllRoles()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ROLE_X, 
				TestObjectContainer.ROLE_Y, TestObjectContainer.ROLE_Z)));
		
		Set<IRole> roles = securityDomainResourceClient.obtainAllRoles();

		assertFalse(roles.isEmpty());
		assertEquals(3, roles.size());
		System.out.println("Result: " + roles);
		
		Set<OpenURRole> resultSet = roles.stream().map(r -> (OpenURRole) r).collect(Collectors.toSet());
		
		OpenURRole r = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ROLE_X.getIdentifier());
		assertTrue(new RoleComparer().objectsAreEqual(TestObjectContainer.ROLE_X, r));
		r = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ROLE_Y.getIdentifier());
		assertTrue(new RoleComparer().objectsAreEqual(TestObjectContainer.ROLE_Y, r));
		r = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ROLE_Z.getIdentifier());
		assertTrue(new RoleComparer().objectsAreEqual(TestObjectContainer.ROLE_Z, r));
	}

	@Test
	public void testFindPermissionById()
	{
		Mockito.when(securityDomainServicesMock.findPermissionById(TestObjectContainer.PERMISSION_1_A.getIdentifier())).thenReturn(
				TestObjectContainer.PERMISSION_1_A);
		
		IPermission p = securityDomainResourceClient.findPermissionById(TestObjectContainer.PERMISSION_1_A.getIdentifier());

		assertNotNull(p);
		System.out.println("Result: " + p);
		
		assertTrue(new PermissionComparer().objectsAreEqual((OpenURPermission) p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testFindPermission()
	{
		Mockito.when(securityDomainServicesMock.findPermission(TestObjectContainer.PERMISSION_1_A.getPermissionText(), 
				TestObjectContainer.APP_A.getApplicationName())).thenReturn(TestObjectContainer.PERMISSION_1_A);
		
		IPermission p = securityDomainResourceClient.findPermission(
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName());

		assertNotNull(p);
		System.out.println("Result: " + p);
		
		assertTrue(new PermissionComparer().objectsAreEqual((OpenURPermission) p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testObtainPermissionsForApp()
	{
		Mockito.when(securityDomainServicesMock.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName())).thenReturn(
				new HashSet<>(Arrays.asList(TestObjectContainer.PERMISSION_1_A, TestObjectContainer.PERMISSION_2_A)));
		
		Set<IPermission> permissions = securityDomainResourceClient.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName());

		assertFalse(permissions.isEmpty());
		assertEquals(2, permissions.size());
		System.out.println("Result: " + permissions);
		
		Set<OpenURPermission> resultSet = permissions.stream().map(p -> (OpenURPermission) p).collect(Collectors.toSet());
		
		OpenURPermission p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERMISSION_1_A.getIdentifier());
		assertTrue(new PermissionComparer().objectsAreEqual(TestObjectContainer.PERMISSION_1_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERMISSION_2_A.getIdentifier());
		assertTrue(new PermissionComparer().objectsAreEqual(TestObjectContainer.PERMISSION_2_A, p));
	}

	@Test
	public void testObtainAllPermissions()
	{
		Mockito.when(securityDomainServicesMock.obtainAllPermissions()).thenReturn(new HashSet<>(Arrays.asList(
				TestObjectContainer.PERMISSION_1_A, TestObjectContainer.PERMISSION_1_B, TestObjectContainer.PERMISSION_1_C,
				TestObjectContainer.PERMISSION_2_A, TestObjectContainer.PERMISSION_2_B, TestObjectContainer.PERMISSION_2_C)));
		
		Set<IPermission> permissions = securityDomainResourceClient.obtainAllPermissions();

		assertFalse(permissions.isEmpty());
		assertEquals(6, permissions.size());
		System.out.println("Result: " + permissions);
		
		Set<OpenURPermission> resultSet = permissions.stream().map(p -> (OpenURPermission) p).collect(Collectors.toSet());
		
		OpenURPermission p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERMISSION_1_A.getIdentifier());
		assertTrue(new PermissionComparer().objectsAreEqual(TestObjectContainer.PERMISSION_1_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERMISSION_1_B.getIdentifier());
		assertTrue(new PermissionComparer().objectsAreEqual(TestObjectContainer.PERMISSION_1_B, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERMISSION_2_A.getIdentifier());
		assertTrue(new PermissionComparer().objectsAreEqual(TestObjectContainer.PERMISSION_2_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERMISSION_2_B.getIdentifier());
		assertTrue(new PermissionComparer().objectsAreEqual(TestObjectContainer.PERMISSION_2_B, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERMISSION_1_C.getIdentifier());
		assertTrue(new PermissionComparer().objectsAreEqual(TestObjectContainer.PERMISSION_1_C, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERMISSION_2_C.getIdentifier());
		assertTrue(new PermissionComparer().objectsAreEqual(TestObjectContainer.PERMISSION_2_C, p));
	}
}
