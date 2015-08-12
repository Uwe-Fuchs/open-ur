package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.remoting.xchange.marshalling.json.AuthorizableMemberSerializer;
import org.openur.remoting.xchange.marshalling.json.AuthorizableOrgUnitSerializer;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;
import org.openur.remoting.xchange.rest.providers.json.UserSetProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OrgUnitResourceTest
	extends JerseyTest
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
				.register(UserSetProvider.class)
				.register(binder);

		return config;
	}

	@Test
	public void testFindOrgUnitById()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/orgunit/id/" + TestObjectContainer.ORG_UNIT_UUID_A)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(AuthorizableOrgUnit.class, new AuthorizableOrgUnitSerializer());
    gsonBuilder.registerTypeAdapter(AuthorizableMember.class, new AuthorizableMemberSerializer());
    gsonBuilder.registerTypeAdapter(Person.class, new PersonSerializer());
    gsonBuilder.registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
    Gson gson = gsonBuilder.create();

		AuthorizableOrgUnit o = gson.fromJson(result, AuthorizableOrgUnit.class);
		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.ORG_UNIT_A, o));

		response.close();
		client.close();
	}

//	@Test
//	public void testFindOrgUnitByNumber()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainAllOrgUnits()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainSubOrgUnitsForOrgUnit()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainRootOrgUnits()
//	{
//		fail("Not yet implemented");
//	}
}
