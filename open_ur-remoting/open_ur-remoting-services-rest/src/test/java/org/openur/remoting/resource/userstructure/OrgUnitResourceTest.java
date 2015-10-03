package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.AuthorizableOrgUnitComparer;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.marshalling.json.AuthorizableMemberSerializer;
import org.openur.remoting.xchange.marshalling.json.AuthorizableOrgUnitSerializer;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class OrgUnitResourceTest
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
				bindFactory(MockOrgUnitServicesFactory.class).to(IOrgUnitServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(OrgUnitResource.class)
				.register(OrgUnitProvider.class)
				.register(IdentifiableEntitySetProvider.class)
				.register(binder);

		return config;
	}

	@Test
	public void testFindOrgUnitAndMembersById()
	{
		String result = performRestCall("userstructure/orgunit/id/" + TestObjectContainer.ORG_UNIT_UUID_A + "?inclMembers=true", String.class);

		AuthorizableOrgUnit o = buildGson().fromJson(result, AuthorizableOrgUnit.class);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersById()
	{		
		String result = performRestCall("userstructure/orgunit/id/" + TestObjectContainer.ORG_UNIT_UUID_A, String.class);

		AuthorizableOrgUnit o = buildGson().fromJson(result, AuthorizableOrgUnit.class);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, o));
		assertTrue(o.getMembers().isEmpty());		
	}

	@Test
	public void testFindOrgUnitAndMembersByNumber()
	{
		String result = performRestCall("userstructure/orgunit/number/" + TestObjectContainer.ORG_UNIT_NUMBER_A + "?inclMembers=true", String.class);

		AuthorizableOrgUnit o = buildGson().fromJson(result, AuthorizableOrgUnit.class);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersByNumber()
	{
		String result = performRestCall("userstructure/orgunit/number/" + TestObjectContainer.ORG_UNIT_NUMBER_A, String.class);

		AuthorizableOrgUnit o = buildGson().fromJson(result, AuthorizableOrgUnit.class);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, o));
		assertTrue(o.getMembers().isEmpty());						
	}

	@Test
	public void testObtainAllOrgUnits()
	{
		String result = performRestCall("userstructure/orgunit/all", String.class);

		Type resultType = new TypeToken<Set<AuthorizableOrgUnit>>()
		{
		}.getType();

    Set<AuthorizableOrgUnit> resultSet = buildGson().fromJson(result, resultType);

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_B);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_B, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_C);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_C, p));
	}

	@Test
	public void testObtainSubOrgUnitsForOrgUnit_inclMembers()
	{
		String result = performRestCall("userstructure/orgunit/sub/" + TestObjectContainer.SUPER_OU_UUID_1 + "?inclMembers=true", String.class);

		Type resultType = new TypeToken<Set<AuthorizableOrgUnit>>()
		{
		}.getType();

    Set<AuthorizableOrgUnit> resultSet = buildGson().fromJson(result, resultType);

		assertFalse(resultSet.isEmpty());
		assertEquals(2, resultSet.size());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertFalse(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_B);
		assertFalse(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_B, p));
	}

	@Test
	public void testObtainSubOrgUnitsForOrgUnit_exclMembers()
	{
		String result = performRestCall("userstructure/orgunit/sub/" + TestObjectContainer.SUPER_OU_UUID_1 + "?inclMembers=false", String.class);

		Type resultType = new TypeToken<Set<AuthorizableOrgUnit>>()
		{
		}.getType();

    Set<AuthorizableOrgUnit> resultSet = buildGson().fromJson(result, resultType);

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, p));
		
		// default value for query-param => without members:
		result = performRestCall("userstructure/orgunit/sub/" + TestObjectContainer.SUPER_OU_UUID_1, String.class);
    resultSet = buildGson().fromJson(result, resultType);

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, p));		
	}

	@Test
	public void testObtainRootOrgUnits()
	{
		String result = performRestCall("userstructure/orgunit/root", String.class);

		Type resultType = new TypeToken<Set<AuthorizableOrgUnit>>()
		{
		}.getType();

    Set<AuthorizableOrgUnit> resultSet = buildGson().fromJson(result, resultType);

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ROOT_OU_UUID);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ROOT_OU, p));
	}
	
	private Gson buildGson()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(AuthorizableOrgUnit.class, new AuthorizableOrgUnitSerializer());
    gsonBuilder.registerTypeAdapter(AuthorizableMember.class, new AuthorizableMemberSerializer());
    gsonBuilder.registerTypeAdapter(Person.class, new PersonSerializer());
    gsonBuilder.registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
    
    return gsonBuilder.create();
	}
}
