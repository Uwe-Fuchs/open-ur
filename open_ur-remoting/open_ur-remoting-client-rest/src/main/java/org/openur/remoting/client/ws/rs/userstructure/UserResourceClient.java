package org.openur.remoting.client.ws.rs.userstructure;

import static org.openur.remoting.resource.userstructure.UserResource.ALL_PERSONS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.ALL_TECHUSERS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.PERSON_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.PERSON_PER_NUMBER_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.TECHUSER_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.TECHUSER_PER_NUMBER_RESOURCE_PATH;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.openur.module.domain.security.authorization.AuthorizableTechUser;
import org.openur.module.domain.security.authorization.IAuthorizableTechUser;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.client.ws.rs.secure_api.AbstractResourceClient;
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
		super(baseUrl + UserResource.USER_RESOURCE_PATH, PersonProvider.class, TechnicalUserProvider.class, IdentifiableEntitySetProvider.class);
	}

	@Override
	public IPerson findPersonById(String personId)
	{
		String url = new StringBuilder()
				.append(PERSON_PER_ID_RESOURCE_PATH)
				.append(personId)
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, Person.class);
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		String url = new StringBuilder()
				.append(PERSON_PER_NUMBER_RESOURCE_PATH)
				.append(personalNumber)
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, Person.class);
	}

	@Override
	public Set<IPerson> obtainAllPersons()
	{
		return performRestCall_GET(
					ALL_PERSONS_RESOURCE_PATH, MediaType.APPLICATION_JSON, new GenericType<Set<IPerson>>(new ParameterizedTypeImpl(Set.class, Person.class)));
	}

	@Override
	public IAuthorizableTechUser findTechnicalUserById(String techUserId)
	{
		String url = new StringBuilder()
				.append(TECHUSER_PER_ID_RESOURCE_PATH)
				.append(techUserId)
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, AuthorizableTechUser.class);
	}

	@Override
	public IAuthorizableTechUser findTechnicalUserByNumber(String techUserNumber)
	{
		String url = new StringBuilder()
				.append(TECHUSER_PER_NUMBER_RESOURCE_PATH)
				.append(techUserNumber)
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, AuthorizableTechUser.class);
	}

	@Override
	public Set<IAuthorizableTechUser> obtainAllTechnicalUsers()
	{
		return performRestCall_GET(
					ALL_TECHUSERS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
					new GenericType<Set<IAuthorizableTechUser>>(new ParameterizedTypeImpl(Set.class, AuthorizableTechUser.class)));
	}
}
