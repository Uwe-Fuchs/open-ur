package org.openur.remoting.client.ws.rs.security;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PermissionComparer;
import org.openur.module.domain.utils.compare.RoleComparer;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.remoting.resource.security.MockSecurityDomainServicesFactory;
import org.openur.remoting.resource.security.SecurityDomainResource;

public class SecurityDomainResourceClientTest
	extends JerseyTest
{
	private ISecurityDomainServices securityDomainServices;
	
	@Override
	protected Application configure()
	{
		// Http-Testserver:
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockSecurityDomainServicesFactory.class).to(ISecurityDomainServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(SecurityDomainResource.class)
				.register(binder);
		
		// Client:
		securityDomainServices = new SecurityDomainResourceClient("http://localhost:9998/" + SecurityDomainResource.SECURITY_DOMAIN_RESOURCE_PATH);		
		
		for (Class<?> provider : ((SecurityDomainResourceClient) securityDomainServices).getProviders())
		{
			config.register(provider);
		}

		return config;
	}

	@Test
	public void testFindRoleById()
	{
		IRole r = securityDomainServices.findRoleById(TestObjectContainer.ROLE_X.getIdentifier());

		assertNotNull(r);
		System.out.println("Result: " + r);
		
		assertTrue(new RoleComparer().objectsAreEqual((OpenURRole) r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testFindRoleByName()
	{
		IRole r = securityDomainServices.findRoleByName(TestObjectContainer.ROLE_X.getRoleName());

		assertNotNull(r);
		System.out.println("Result: " + r);
		
		assertTrue(new RoleComparer().objectsAreEqual((OpenURRole) r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testObtainAllRoles()
	{
		Set<IRole> roles = securityDomainServices.obtainAllRoles();

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
		IPermission p = securityDomainServices.findPermissionById(TestObjectContainer.PERMISSION_1_A.getIdentifier());

		assertNotNull(p);
		System.out.println("Result: " + p);
		
		assertTrue(new PermissionComparer().objectsAreEqual((OpenURPermission) p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testFindPermission()
	{
		IPermission p = securityDomainServices.findPermission(
				TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName());

		assertNotNull(p);
		System.out.println("Result: " + p);
		
		assertTrue(new PermissionComparer().objectsAreEqual((OpenURPermission) p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testObtainPermissionsForApp()
	{
		Set<IPermission> permissions = securityDomainServices.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName());

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
		Set<IPermission> permissions = securityDomainServices.obtainAllPermissions();

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
