package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.ALL_ORGUNITS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.ALL_ROOT_ORGUNITS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.ORGUNIT_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.ORGUNIT_PER_NUMBER_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.ORGUNIT_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.SUB_ORGUNITS_RESOURCE_PATH;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.orgunit.OrgUnitFull;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.OrgUnitComparer;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;

public class OrgUnitResourceTest
	extends AbstractResourceTest
{
	private IOrgUnitServices orgUnitServicesMock;

	@Override
	protected Application configure()
	{
		orgUnitServicesMock = Mockito.mock(IOrgUnitServices.class);
		
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(orgUnitServicesMock).to(IOrgUnitServices.class);
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
		
		getResourceClient().addProvider(OrgUnitProvider.class);
		getResourceClient().addProvider(IdentifiableEntitySetProvider.class);
	}

	@Test
	public void testFindOrgUnitAndMembersById()
	{
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		
		OrgUnitFull o = getResourceClient().performRestCall_GET(
				ORGUNIT_PER_ID_RESOURCE_PATH + TestObjectContainer.ORG_UNIT_UUID_A 
				+ "?inclMembers=true", MediaType.APPLICATION_JSON, OrgUnitFull.class);

		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersById()
	{		
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.FALSE))
				.thenReturn(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS);
		
		OrgUnitFull o = getResourceClient().performRestCall_GET(
				ORGUNIT_PER_ID_RESOURCE_PATH + TestObjectContainer.ORG_UNIT_UUID_A, MediaType.APPLICATION_JSON, OrgUnitFull.class);

		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, o));
		assertTrue(o.getMembers().isEmpty());		
	}

	@Test
	public void testFindOrgUnitAndMembersByNumber()
	{
		Mockito.when(orgUnitServicesMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		
		OrgUnitFull o = getResourceClient().performRestCall_GET(
				ORGUNIT_PER_NUMBER_RESOURCE_PATH + TestObjectContainer.ORG_UNIT_NUMBER_A 
				+ "?inclMembers=true", MediaType.APPLICATION_JSON, OrgUnitFull.class);

		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersByNumber()
	{
		Mockito.when(orgUnitServicesMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.FALSE))
				.thenReturn(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS);
		
		OrgUnitFull o = getResourceClient().performRestCall_GET(
				ORGUNIT_PER_NUMBER_RESOURCE_PATH + TestObjectContainer.ORG_UNIT_NUMBER_A, MediaType.APPLICATION_JSON, OrgUnitFull.class);

		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, o));
		assertTrue(o.getMembers().isEmpty());						
	}

	@Test
	public void testObtainAllOrgUnits()
	{
		Mockito.when(orgUnitServicesMock.obtainAllOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B, 
				TestObjectContainer.ORG_UNIT_C)));
		
		Set<OrgUnitFull> resultSet = getResourceClient().performRestCall_GET(ALL_ORGUNITS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
				new GenericType<Set<OrgUnitFull>>(new ParameterizedTypeImpl(Set.class, OrgUnitFull.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		
		OrgUnitFull p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_B);
		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_B, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_C);
		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_C, p));
	}

	@Test
	public void testObtainSubOrgUnitsForOrgUnit_inclMembers()
	{
		Mockito.when(orgUnitServicesMock.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.TRUE)).thenReturn(
				new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B)));
		
		Set<OrgUnitFull> resultSet = getResourceClient().performRestCall_GET(
				SUB_ORGUNITS_RESOURCE_PATH + TestObjectContainer.SUPER_OU_UUID_1 + "?inclMembers=true", MediaType.APPLICATION_JSON, 
				new GenericType<Set<OrgUnitFull>>(new ParameterizedTypeImpl(Set.class, OrgUnitFull.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(2, resultSet.size());
		
		OrgUnitFull p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertFalse(p.getMembers().isEmpty());
		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_B);
		assertFalse(p.getMembers().isEmpty());
		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_B, p));
	}

	@Test
	public void testObtainSubOrgUnitsForOrgUnit_exclMembers()
	{
		Mockito.when(orgUnitServicesMock.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.FALSE)).thenReturn(
				new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS)));
		
		Set<OrgUnitFull> resultSet = getResourceClient().performRestCall_GET(
				SUB_ORGUNITS_RESOURCE_PATH + TestObjectContainer.SUPER_OU_UUID_1 + "?inclMembers=false", MediaType.APPLICATION_JSON, 
				new GenericType<Set<OrgUnitFull>>(new ParameterizedTypeImpl(Set.class, OrgUnitFull.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		OrgUnitFull p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(p.getMembers().isEmpty());
		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, p));
		
		// default value for query-param => without members:
		resultSet = getResourceClient().performRestCall_GET(
				SUB_ORGUNITS_RESOURCE_PATH + TestObjectContainer.SUPER_OU_UUID_1, MediaType.APPLICATION_JSON, 
				new GenericType<Set<OrgUnitFull>>(new ParameterizedTypeImpl(Set.class, OrgUnitFull.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(p.getMembers().isEmpty());
		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, p));		
	}

	@Test
	public void testObtainRootOrgUnits()
	{
		Mockito.when(orgUnitServicesMock.obtainRootOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ROOT_OU)));
		
		Set<OrgUnitFull> resultSet = getResourceClient().performRestCall_GET(ALL_ROOT_ORGUNITS_RESOURCE_PATH, MediaType.APPLICATION_JSON,
				new GenericType<Set<OrgUnitFull>>(new ParameterizedTypeImpl(Set.class, OrgUnitFull.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		
		OrgUnitFull p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.ROOT_OU_UUID);
		assertTrue(new OrgUnitComparer().objectsAreEqual(TestObjectContainer.ROOT_OU, p));
	}

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + ORGUNIT_RESOURCE_PATH;
	}
}
