package org.openur.remoting.client.ws.rs.userstructure;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
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
	@Inject
	public UserResourceClient(String baseUrl)
	{
		super(baseUrl, PersonProvider.class, TechnicalUserProvider.class, IdentifiableEntitySetProvider.class);
	}

	@Override
	public IPerson findPersonById(String personId)
	{
		String url = getBaseUrl() + UserResource.USER_RESOURCE_PATH + UserResource.PERSON_PER_ID_RESOURCE_PATH + personId;
		
		return performRestCall(url, Person.class);
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		String url = getBaseUrl() + UserResource.USER_RESOURCE_PATH + UserResource.PERSON_PER_NUMBER_RESOURCE_PATH + personalNumber;
		
		return performRestCall(url, Person.class);
	}

	@Override
	public Set<IPerson> obtainAllPersons()
	{
		String url = getBaseUrl() + UserResource.USER_RESOURCE_PATH + UserResource.ALL_PERSONS_RESOURCE_PATH;
		
		return performRestCall(url, new GenericType<Set<IPerson>>(new ParameterizedTypeImpl(Set.class, Person.class)));
	}

	@Override
	public ITechnicalUser findTechnicalUserById(String techUserId)
	{
		String url = getBaseUrl() + UserResource.USER_RESOURCE_PATH + UserResource.TECHUSER_PER_ID_RESOURCE_PATH + techUserId;
		
		return performRestCall(url, TechnicalUser.class);
	}

	@Override
	public ITechnicalUser findTechnicalUserByNumber(String techUserNumber)
	{
		String url = getBaseUrl() + UserResource.USER_RESOURCE_PATH + UserResource.TECHUSER_PER_NUMBER_RESOURCE_PATH + techUserNumber;
		
		return performRestCall(url, TechnicalUser.class);
	}

	@Override
	public Set<ITechnicalUser> obtainAllTechnicalUsers()
	{
		String url = getBaseUrl() + UserResource.USER_RESOURCE_PATH + UserResource.ALL_TECHUSERS_RESOURCE_PATH;
		
		return performRestCall(url, new GenericType<Set<ITechnicalUser>>(new ParameterizedTypeImpl(Set.class, TechnicalUser.class)));
	}
}
