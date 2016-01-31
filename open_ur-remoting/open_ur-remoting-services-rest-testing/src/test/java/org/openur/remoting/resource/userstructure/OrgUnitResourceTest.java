package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.*;

import static org.openur.remoting.resource.userstructure.OrgUnitResource.*;

import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.AuthorizableOrgUnitComparer;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;

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
	
	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		
		getMyClient().register(OrgUnitProvider.class);
		getMyClient().register(IdentifiableEntitySetProvider.class);
	}

	@Test
	public void testFindOrgUnitAndMembersById()
	{
		AuthorizableOrgUnit o = performRestCall_Get(
				ORGUNIT_RESOURCE_PATH + ORGUNIT_PER_ID_RESOURCE_PATH + TestObjectContainer.ORG_UNIT_UUID_A 
				+ "?inclMembers=true", AuthorizableOrgUnit.class);

		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersById()
	{		
		AuthorizableOrgUnit o = performRestCall_Get(
				ORGUNIT_RESOURCE_PATH + ORGUNIT_PER_ID_RESOURCE_PATH + TestObjectContainer.ORG_UNIT_UUID_A, AuthorizableOrgUnit.class);

		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, o));
		assertTrue(o.getMembers().isEmpty());		
	}

	@Test
	public void testFindOrgUnitAndMembersByNumber()
	{
		AuthorizableOrgUnit o = performRestCall_Get(
				ORGUNIT_RESOURCE_PATH + ORGUNIT_PER_NUMBER_RESOURCE_PATH + TestObjectContainer.ORG_UNIT_NUMBER_A 
				+ "?inclMembers=true", AuthorizableOrgUnit.class);

		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersByNumber()
	{
		AuthorizableOrgUnit o = performRestCall_Get(
				ORGUNIT_RESOURCE_PATH + ORGUNIT_PER_NUMBER_RESOURCE_PATH + TestObjectContainer.ORG_UNIT_NUMBER_A, AuthorizableOrgUnit.class);

		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, o));
		assertTrue(o.getMembers().isEmpty());						
	}

	@Test
	public void testObtainAllOrgUnits()
	{
		Set<AuthorizableOrgUnit> resultSet = performRestCall_Get(ORGUNIT_RESOURCE_PATH + ALL_ORGUNITS_RESOURCE_PATH, 
				new GenericType<Set<AuthorizableOrgUnit>>(new ParameterizedTypeImpl(Set.class, AuthorizableOrgUnit.class)));

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
		Set<AuthorizableOrgUnit> resultSet = performRestCall_Get(
				ORGUNIT_RESOURCE_PATH + SUB_ORGUNITS_RESOURCE_PATH + TestObjectContainer.SUPER_OU_UUID_1 + "?inclMembers=true", 
				new GenericType<Set<AuthorizableOrgUnit>>(new ParameterizedTypeImpl(Set.class, AuthorizableOrgUnit.class)));

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
		Set<AuthorizableOrgUnit> resultSet = performRestCall_Get(
				ORGUNIT_RESOURCE_PATH + SUB_ORGUNITS_RESOURCE_PATH + TestObjectContainer.SUPER_OU_UUID_1 + "?inclMembers=false", 
				new GenericType<Set<AuthorizableOrgUnit>>(new ParameterizedTypeImpl(Set.class, AuthorizableOrgUnit.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, p));
		
		// default value for query-param => without members:
		resultSet = performRestCall_Get(
				ORGUNIT_RESOURCE_PATH + SUB_ORGUNITS_RESOURCE_PATH + TestObjectContainer.SUPER_OU_UUID_1, 
				new GenericType<Set<AuthorizableOrgUnit>>(new ParameterizedTypeImpl(Set.class, AuthorizableOrgUnit.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, p));		
	}

	@Test
	public void testObtainRootOrgUnits()
	{
		Set<AuthorizableOrgUnit> resultSet = performRestCall_Get(ORGUNIT_RESOURCE_PATH + ALL_ROOT_ORGUNITS_RESOURCE_PATH,
				new GenericType<Set<AuthorizableOrgUnit>>(new ParameterizedTypeImpl(Set.class, AuthorizableOrgUnit.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ROOT_OU_UUID);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ROOT_OU, p));
	}
}
