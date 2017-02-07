package org.openur.remoting.resource.securitydomain;

import static org.junit.Assert.*;
import static org.openur.remoting.resource.securitydomain.SecurityDomainResource.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PermissionComparer;
import org.openur.module.domain.utils.compare.RoleComparer;
import org.openur.module.service.securitydomain.ISecurityDomainServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.resource.securitydomain.SecurityDomainResource;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.PermissionProvider;
import org.openur.remoting.xchange.rest.providers.json.RoleProvider;

public class SecurityDomainResourceTest
	extends AbstractResourceTest
{
	private ISecurityDomainServices securityDomainServicesMock;
	
	@Override
	protected Application configure()
	{		
		securityDomainServicesMock = Mockito.mock(ISecurityDomainServices.class);
		
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(securityDomainServicesMock).to(ISecurityDomainServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(SecurityDomainResource.class)
				.register(PermissionProvider.class)
				.register(RoleProvider.class)
				.register(IdentifiableEntitySetProvider.class)
				.register(binder);

		return config;
	}
	
	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		
		getResourceClient().addProvider(PermissionProvider.class);
		getResourceClient().addProvider(RoleProvider.class);
		getResourceClient().addProvider(IdentifiableEntitySetProvider.class);
	}
	
	@Test
	public void testFindRoleById()
	{
		Mockito.when(securityDomainServicesMock.findRoleById(TestObjectContainer.ROLE_X.getIdentifier())).thenReturn(
				TestObjectContainer.ROLE_X);		
		
		OpenURRole r = getResourceClient().performRestCall_GET(ROLE_PER_ID_RESOURCE_PATH 
				+ TestObjectContainer.ROLE_X.getIdentifier(), MediaType.APPLICATION_JSON, OpenURRole.class);
		
		assertTrue(new RoleComparer().objectsAreEqual(r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testFindRoleByName()
	{
		Mockito.when(securityDomainServicesMock.findRoleByName(TestObjectContainer.ROLE_X.getRoleName())).thenReturn(
				TestObjectContainer.ROLE_X);
		
		OpenURRole r = getResourceClient().performRestCall_GET(ROLE_PER_NAME_RESOURCE_PATH 
				+ TestObjectContainer.ROLE_X.getRoleName(), MediaType.APPLICATION_JSON, OpenURRole.class);
		
		assertTrue(new RoleComparer().objectsAreEqual(r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testObtainAllRoles()
	{
		Mockito.when(securityDomainServicesMock.obtainAllRoles()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ROLE_X, 
				TestObjectContainer.ROLE_Y, TestObjectContainer.ROLE_Z)));
		
		Set<OpenURRole> resultSet = getResourceClient().performRestCall_GET(ALL_ROLES_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
				new GenericType<Set<OpenURRole>>(new ParameterizedTypeImpl(Set.class, OpenURRole.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		
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
		
		OpenURPermission p = getResourceClient().performRestCall_GET(PERMISSION_PER_ID_RESOURCE_PATH 
					+ TestObjectContainer.PERMISSION_1_A.getIdentifier(), MediaType.APPLICATION_JSON, OpenURPermission.class);
		
		assertTrue(EqualsBuilder.reflectionEquals(p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testFindPermissionByText()
	{
		Mockito.when(securityDomainServicesMock.findPermission(TestObjectContainer.PERMISSION_1_A.getPermissionText(), 
					TestObjectContainer.APP_A.getApplicationName())).thenReturn(TestObjectContainer.PERMISSION_1_A);
		
		OpenURPermission p = getResourceClient().performRestCall_GET(PERMISSION_PER_TEXT_RESOURCE_PATH 
					+ "?text=" + TestObjectContainer.PERMISSION_1_A.getPermissionText() 
					+ "&appName=" + TestObjectContainer.APP_A.getApplicationName(), MediaType.APPLICATION_JSON, OpenURPermission.class);
		
		assertTrue(EqualsBuilder.reflectionEquals(p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testObtainPermissionsForApp()
	{
		Mockito.when(securityDomainServicesMock.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName())).thenReturn(
					new HashSet<>(Arrays.asList(TestObjectContainer.PERMISSION_1_A, TestObjectContainer.PERMISSION_2_A)));
		
		Set<OpenURPermission> resultSet = getResourceClient().performRestCall_GET(PERMISSIONS_PER_APP_RESOURCE_PATH 
					+ TestObjectContainer.APP_A.getApplicationName(), MediaType.APPLICATION_JSON, 
					new GenericType<Set<OpenURPermission>>(new ParameterizedTypeImpl(Set.class, OpenURPermission.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(2, resultSet.size());
		
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
		
		Set<OpenURPermission> resultSet = getResourceClient().performRestCall_GET(ALL_PERMISSIONS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
				new GenericType<Set<OpenURPermission>>(new ParameterizedTypeImpl(Set.class, OpenURPermission.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(6, resultSet.size());
		
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

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + SECURITY_DOMAIN_RESOURCE_PATH;
	}
}
