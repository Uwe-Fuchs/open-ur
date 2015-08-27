package org.openur.remoting.resource.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PermissionComparer;
import org.openur.module.domain.utils.compare.RoleComparer;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.PermissionProvider;
import org.openur.remoting.xchange.rest.providers.json.RoleProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
	
	@Test
	public void testFindRoleById()
	{
		String result = performRestCall("securitydomain/role/id/" + TestObjectContainer.ROLE_X.getIdentifier());

		OpenURRole r = buildGson().fromJson(result, OpenURRole.class);
		assertTrue(new RoleComparer().objectsAreEqual(r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testFindRoleByName()
	{
		String result = performRestCall("securitydomain/role/name/" + TestObjectContainer.ROLE_X.getRoleName());

		OpenURRole r = buildGson().fromJson(result, OpenURRole.class);
		assertTrue(new RoleComparer().objectsAreEqual(r, TestObjectContainer.ROLE_X));
	}

	@Test
	public void testObtainAllRoles()
	{
		String result = performRestCall("securitydomain/role/all");

		Type resultType = new TypeToken<Set<OpenURRole>>()
		{
		}.getType();

    Set<OpenURRole> resultSet = buildGson().fromJson(result, resultType);

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
		String result = performRestCall("securitydomain/permission/id/" + TestObjectContainer.PERMISSION_1_A.getIdentifier());

		OpenURPermission p = new Gson().fromJson(result, OpenURPermission.class);
		assertTrue(EqualsBuilder.reflectionEquals(p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testFindPermissionByText()
	{
		String result = performRestCall("securitydomain/permission/text/" + TestObjectContainer.PERMISSION_1_A.getPermissionText());

		OpenURPermission p = new Gson().fromJson(result, OpenURPermission.class);
		assertTrue(EqualsBuilder.reflectionEquals(p, TestObjectContainer.PERMISSION_1_A));
	}

	@Test
	public void testObtainPermissionsForApp()
	{
		String result = performRestCall("securitydomain/permission/app/" + TestObjectContainer.APP_A.getApplicationName());

		Type resultType = new TypeToken<Set<OpenURPermission>>()
		{
		}.getType();

    Set<OpenURPermission> resultSet = new Gson().fromJson(result, resultType);

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
		String result = performRestCall("securitydomain/permission/all");

		Type resultType = new TypeToken<Set<OpenURPermission>>()
		{
		}.getType();

    Set<OpenURPermission> resultSet = new Gson().fromJson(result, resultType);

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
	
	private Gson buildGson()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
    
    return gsonBuilder.create();
	}
}
