package org.openur.remoting.client.ws.rs.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.AuthorizableOrgUnitComparer;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.remoting.resource.userstructure.OrgUnitResource;

public class OrgUnitResourceClientTest
	extends JerseyTest
{
	private IOrgUnitServices orgUnitResourceClient;
	private IOrgUnitServices orgUnitServicesMock;

	@Override
	protected Application configure()
	{
		// mocked service:
		orgUnitServicesMock = Mockito.mock(IOrgUnitServices.class);
		
		// Http-Testserver:
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(orgUnitServicesMock).to(IOrgUnitServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(OrgUnitResource.class)
				.register(binder);

		// Client:
		orgUnitResourceClient = new OrgUnitResourceClient("http://localhost:9998/");		
		
		for (Class<?> provider : ((OrgUnitResourceClient) orgUnitResourceClient).getProviders())
		{
			config.register(provider);
		}

		return config;
	}

	@Test
	public void testFindOrgUnitAndMembersById()
	{
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		
		IAuthorizableOrgUnit o = orgUnitResourceClient.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.TRUE);

		System.out.println("Result: " + o);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, (AuthorizableOrgUnit) o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersById()
	{		
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.FALSE))
				.thenReturn(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS);
		
		IAuthorizableOrgUnit o = orgUnitResourceClient.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.FALSE);

		System.out.println("Result: " + o);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, (AuthorizableOrgUnit) o));
		assertTrue(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitAndMembersByNumber()
	{
		Mockito.when(orgUnitServicesMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		
		IAuthorizableOrgUnit o = orgUnitResourceClient.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.TRUE);

		System.out.println("Result: " + o);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, (AuthorizableOrgUnit) o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersByNumber()
	{
		Mockito.when(orgUnitServicesMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.FALSE))
				.thenReturn(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS);

		IAuthorizableOrgUnit o = orgUnitResourceClient.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.FALSE);

		System.out.println("Result: " + o);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, (AuthorizableOrgUnit) o));
		assertTrue(o.getMembers().isEmpty());
	}

	@Test
	public void testObtainAllOrgUnits()
	{
		Mockito.when(orgUnitServicesMock.obtainAllOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B, 
				TestObjectContainer.ORG_UNIT_C)));
	
		Set<IAuthorizableOrgUnit> resultSet = orgUnitResourceClient.obtainAllOrgUnits();

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		System.out.println("Result: " + resultSet);
		
		Set<AuthorizableOrgUnit> allOrgUnits = resultSet.stream().map(o -> (AuthorizableOrgUnit) o).collect(Collectors.toSet());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(allOrgUnits, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(allOrgUnits, TestObjectContainer.ORG_UNIT_UUID_B);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_B, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(allOrgUnits, TestObjectContainer.ORG_UNIT_UUID_C);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_C, p));
	}

	@Test
	public void testObtainSubOrgUnitsForOrgUnit_inclMembers()
	{
		Mockito.when(orgUnitServicesMock.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.TRUE)).thenReturn(
				new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B)));
		
		Set<IAuthorizableOrgUnit> resultSet = orgUnitResourceClient.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.TRUE);

		assertFalse(resultSet.isEmpty());
		assertEquals(2, resultSet.size());
		System.out.println("Result: " + resultSet);
		
		Set<AuthorizableOrgUnit> allOrgUnits = resultSet.stream().map(o -> (AuthorizableOrgUnit) o).collect(Collectors.toSet());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(allOrgUnits, TestObjectContainer.ORG_UNIT_UUID_A);
		assertFalse(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(allOrgUnits, TestObjectContainer.ORG_UNIT_UUID_B);
		assertFalse(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_B, p));
	}

	@Test
	public void testObtainSubOrgUnitsForOrgUnit_exclMembers()
	{
		Mockito.when(orgUnitServicesMock.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.FALSE)).thenReturn(
				new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS)));
		
		Set<IAuthorizableOrgUnit> resultSet = orgUnitResourceClient.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.FALSE);

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		System.out.println("Result: " + resultSet);
		
		Set<AuthorizableOrgUnit> allOrgUnits = resultSet.stream().map(o -> (AuthorizableOrgUnit) o).collect(Collectors.toSet());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(allOrgUnits, TestObjectContainer.ORG_UNIT_UUID_A);
		assertTrue(p.getMembers().isEmpty());
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, p));		
	}

	@Test
	public void testObtainRootOrgUnits()
	{
		Mockito.when(orgUnitServicesMock.obtainRootOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ROOT_OU)));
		
		Set<IAuthorizableOrgUnit> resultSet = orgUnitResourceClient.obtainRootOrgUnits();

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		System.out.println("Result: " + resultSet);
		
		Set<AuthorizableOrgUnit> allOrgUnits = resultSet.stream().map(o -> (AuthorizableOrgUnit) o).collect(Collectors.toSet());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(allOrgUnits, TestObjectContainer.ROOT_OU_UUID);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ROOT_OU, p));
	}
}
