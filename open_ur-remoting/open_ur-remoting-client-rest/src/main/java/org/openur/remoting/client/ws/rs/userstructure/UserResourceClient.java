package org.openur.remoting.client.ws.rs.userstructure;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;
import org.openur.remoting.xchange.rest.providers.json.PermissionProvider;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;
import org.openur.remoting.xchange.rest.providers.json.RoleProvider;
import org.openur.remoting.xchange.rest.providers.json.TechnicalUserProvider;

public class UserResourceClient
	implements IUserServices
{
	@Override
	public IPerson findPersonById(String personId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPerson> obtainAllPersons()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITechnicalUser findTechnicalUserById(String techUserId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITechnicalUser findTechnicalUserByNumber(String techUserNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ITechnicalUser> obtainAllTechnicalUsers()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	protected String performRestCall(String url, String mediaType)
	{
		Client client = createJerseyClient();
		
		Response response = client
				.target("http://localhost:9998/" + url)
				.request(mediaType)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		response.close();
		
		System.out.println("Result: " + result);		
		
		return result;
	}
	
	protected String performRestCall(String url)
	{
		return performRestCall(url, MediaType.APPLICATION_JSON);
	}
	
	private Client createJerseyClient()
	{
		ClientConfig clientConfig = new ClientConfig()
			.register(PersonProvider.class)
			.register(TechnicalUserProvider.class)
			.register(OrgUnitProvider.class)
			.register(PermissionProvider.class)
			.register(RoleProvider.class)
			.register(IdentifiableEntitySetProvider.class);
		
		return ClientBuilder.newClient(clientConfig);
	}
}
