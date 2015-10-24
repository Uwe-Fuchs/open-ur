package org.openur.remoting.client.ws.rs.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.AuthorizableOrgUnitComparer;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.remoting.resource.userstructure.MockOrgUnitServicesFactory;
import org.openur.remoting.resource.userstructure.OrgUnitResource;

public class OrgUnitResourceClientTest
	extends JerseyTest
{
	private IOrgUnitServices orgUnitServices;

	@Override
	protected Application configure()
	{
		orgUnitServices = new OrgUnitResourceClient("http://localhost:9998/");
		
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockOrgUnitServicesFactory.class).to(IOrgUnitServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(OrgUnitResource.class)
				.register(binder);
		
		for (Class<?> provider : ((OrgUnitResourceClient) orgUnitServices).getProviders())
		{
			config.register(provider);
		}

		return config;
	}

	@Test
	public void testFindOrgUnitAndMembersById()
	{
		IAuthorizableOrgUnit o = orgUnitServices.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.TRUE);

		System.out.println("Result: " + o);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, (AuthorizableOrgUnit) o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersById()
	{		
		IAuthorizableOrgUnit o = orgUnitServices.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.FALSE);

		System.out.println("Result: " + o);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, (AuthorizableOrgUnit) o));
		assertTrue(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitAndMembersByNumber()
	{
		IAuthorizableOrgUnit o = orgUnitServices.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.TRUE);

		System.out.println("Result: " + o);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A, (AuthorizableOrgUnit) o));
		assertFalse(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitWithoutMembersByNumber()
	{
		IAuthorizableOrgUnit o = orgUnitServices.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.FALSE);

		System.out.println("Result: " + o);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, (AuthorizableOrgUnit) o));
		assertTrue(o.getMembers().isEmpty());
	}

	@Test
	public void testObtainAllOrgUnits()
	{
		Set<IAuthorizableOrgUnit> resultSet = orgUnitServices.obtainAllOrgUnits();

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
		Set<IAuthorizableOrgUnit> resultSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.TRUE);

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
		Set<IAuthorizableOrgUnit> resultSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.FALSE);

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
		Set<IAuthorizableOrgUnit> resultSet = orgUnitServices.obtainRootOrgUnits();

		assertFalse(resultSet.isEmpty());
		assertEquals(1, resultSet.size());
		System.out.println("Result: " + resultSet);
		
		Set<AuthorizableOrgUnit> allOrgUnits = resultSet.stream().map(o -> (AuthorizableOrgUnit) o).collect(Collectors.toSet());
		
		AuthorizableOrgUnit p = DomainObjectHelper.findIdentifiableEntityInCollection(allOrgUnits, TestObjectContainer.ROOT_OU_UUID);
		assertTrue(new AuthorizableOrgUnitComparer().objectsAreEqual(TestObjectContainer.ROOT_OU, p));
	}
}
