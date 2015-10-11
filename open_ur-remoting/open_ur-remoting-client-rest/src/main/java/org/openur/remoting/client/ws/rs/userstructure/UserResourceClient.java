package org.openur.remoting.client.ws.rs.userstructure;

import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.client.ws.rs.AbstractResourceClient;
import org.openur.remoting.resource.userstructure.UserResource;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;
import org.openur.remoting.xchange.rest.providers.json.TechnicalUserProvider;

public class UserResourceClient
	extends AbstractResourceClient
	implements IUserServices
{
	private final String baseUrl;
	
	@Inject
	public UserResourceClient(String baseUrl)
	{
		super(PersonProvider.class, TechnicalUserProvider.class, IdentifiableEntitySetProvider.class);
		
		Validate.notEmpty(baseUrl, "Base-URL must not be empty!");
		this.baseUrl = baseUrl;
	}

	@Override
	public IPerson findPersonById(String personId)
	{
		String url = baseUrl + UserResource.USER_RESOURCE_PATH + UserResource.PERSON_PER_ID_RESOURCE_PATH + personId;
		
		return performRestCall(url, Person.class);
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
}
