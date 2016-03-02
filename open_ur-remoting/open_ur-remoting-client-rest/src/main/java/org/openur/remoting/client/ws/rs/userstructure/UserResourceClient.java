package org.openur.remoting.client.ws.rs.userstructure;

import static org.openur.remoting.resource.userstructure.UserResource.ALL_PERSONS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.ALL_TECHUSERS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.PERSON_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.PERSON_PER_NUMBER_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.TECHUSER_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.TECHUSER_PER_NUMBER_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.USER_RESOURCE_PATH;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.client.ws.rs.AbstractResourceClient;
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
		String url = new StringBuilder()
				.append(getBaseUrl())
				.append(USER_RESOURCE_PATH)
				.append(PERSON_PER_ID_RESOURCE_PATH)
				.append(personId)
				.toString();
		
		return performRestCall(url, MediaType.APPLICATION_JSON, Person.class);
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		String url = new StringBuilder()
				.append(getBaseUrl())
				.append(USER_RESOURCE_PATH)
				.append(PERSON_PER_NUMBER_RESOURCE_PATH)
				.append(personalNumber)
				.toString();
		
		return performRestCall(url, MediaType.APPLICATION_JSON, Person.class);
	}

	@Override
	public Set<IPerson> obtainAllPersons()
	{
		String url = new StringBuilder()
				.append(getBaseUrl())
				.append(USER_RESOURCE_PATH)
				.append(ALL_PERSONS_RESOURCE_PATH)
				.toString();
		
		return performRestCall(url, MediaType.APPLICATION_JSON, new GenericType<Set<IPerson>>(new ParameterizedTypeImpl(Set.class, Person.class)));
	}

	@Override
	public ITechnicalUser findTechnicalUserById(String techUserId)
	{
		String url = new StringBuilder()
				.append(getBaseUrl())
				.append(USER_RESOURCE_PATH)
				.append(TECHUSER_PER_ID_RESOURCE_PATH)
				.append(techUserId)
				.toString();
		
		return performRestCall(url, MediaType.APPLICATION_JSON, TechnicalUser.class);
	}

	@Override
	public ITechnicalUser findTechnicalUserByNumber(String techUserNumber)
	{
		String url = new StringBuilder()
				.append(getBaseUrl())
				.append(USER_RESOURCE_PATH)
				.append(TECHUSER_PER_NUMBER_RESOURCE_PATH)
				.append(techUserNumber)
				.toString();
		
		return performRestCall(url, MediaType.APPLICATION_JSON, TechnicalUser.class);
	}

	@Override
	public Set<ITechnicalUser> obtainAllTechnicalUsers()
	{
		String url = new StringBuilder()
				.append(getBaseUrl())
				.append(USER_RESOURCE_PATH)
				.append(ALL_TECHUSERS_RESOURCE_PATH)
				.toString();
		
		return performRestCall(url, MediaType.APPLICATION_JSON, new GenericType<Set<ITechnicalUser>>(new ParameterizedTypeImpl(Set.class, TechnicalUser.class)));
	}
}
