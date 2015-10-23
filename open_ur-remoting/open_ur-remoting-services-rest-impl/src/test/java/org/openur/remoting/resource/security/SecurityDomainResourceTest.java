package org.openur.remoting.resource.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PermissionComparer;
import org.openur.module.domain.utils.compare.RoleComparer;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.PermissionProvider;
import org.openur.remoting.xchange.rest.providers.json.RoleProvider;

public class SecurityDomainResourceTest
	extends AbstractResourceTest
{
	@Override
	protected Application configure()
	{
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockSecurityDomainServicesFactory.class).to(ISecurityDomainServices.class);
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
		
		getMyClient().register(PermissionProvider.class);
		getMyClient().register(RoleProvider.class);
		getMyClient().register(IdentifiableEntitySetProvider.class);
	}
	
	@Test
	public void testFindRoleById()
	{
		OpenURRole r = performRestCall("securitydomain/role/id/" + TestObjectContainer.ROLE_X.getIdentifier(), OpenURRole.class);
		
		assertTrue(new RoleComparer().objectsAreEqual(r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testFindRoleByName()
	{
		OpenURRole r = performRestCall("securitydomain/role/name/" + TestObjectContainer.ROLE_X.getRoleName(), OpenURRole.class);
		
		assertTrue(new RoleComparer().objectsAreEqual(r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testObtainAllRoles()
	{
		Set<OpenURRole> resultSet = performRestCall("securitydomain/role/all", 
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
		OpenURPermission p = performRestCall("securitydomain/permission/id/" + TestObjectContainer.PERMISSION_1_A.getIdentifier(), OpenURPermission.class);
		
		assertTrue(EqualsBuilder.reflectionEquals(p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testFindPermissionByText()
	{
		OpenURPermission p = performRestCall("securitydomain/permission/text?text=" + TestObjectContainer.PERMISSION_1_A.getPermissionText() 
			+ "&appName=" + TestObjectContainer.APP_A.getApplicationName(), OpenURPermission.class);
		
		assertTrue(EqualsBuilder.reflectionEquals(p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testObtainPermissionsForApp()
	{
		Set<OpenURPermission> resultSet = performRestCall("securitydomain/permission/app/" + TestObjectContainer.APP_A.getApplicationName(), 
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
		Set<OpenURPermission> resultSet = performRestCall("securitydomain/permission/all", 
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
}
