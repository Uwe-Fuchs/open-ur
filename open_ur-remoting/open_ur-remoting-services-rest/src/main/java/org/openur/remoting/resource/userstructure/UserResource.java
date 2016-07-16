package org.openur.remoting.resource.userstructure;

import static org.openur.remoting.resource.secure_api.PermissionConstraints.REMOTE_READ;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openur.module.domain.security.authorization.IAuthorizableTechUser;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.service.userstructure.IUserServices;

@Path(UserResource.USER_RESOURCE_PATH)
public class UserResource
	implements IUserServices
{
	public static final String USER_RESOURCE_PATH = "userstructure/";
	public static final String PERSON_PER_ID_RESOURCE_PATH = "person/id/";
	public static final String PERSON_PER_NUMBER_RESOURCE_PATH = "person/number/";
	public static final String ALL_PERSONS_RESOURCE_PATH = "person/all";
	public static final String TECHUSER_PER_ID_RESOURCE_PATH = "techuser/id/";
	public static final String TECHUSER_PER_NUMBER_RESOURCE_PATH = "techuser/number/";
	public static final String ALL_TECHUSERS_RESOURCE_PATH = "techuser/all";
	
	@Inject
	private IUserServices userServices;

	@Override
	@GET
	@Path(PERSON_PER_ID_RESOURCE_PATH + "{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public IPerson findPersonById(@PathParam("id") String id)
	{
		return userServices.findPersonById(id);
	}

	@Override
	@GET
	@Path(PERSON_PER_NUMBER_RESOURCE_PATH + "{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public IPerson findPersonByNumber(@PathParam("number") String number)
	{
		return userServices.findPersonByNumber(number);
	}

	@Override
	@GET
	@Path(ALL_PERSONS_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public Set<IPerson> obtainAllPersons()
	{
		return userServices.obtainAllPersons();
	}

	@Override
	@GET
	@Path(TECHUSER_PER_ID_RESOURCE_PATH + "{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public IAuthorizableTechUser findTechnicalUserById(@PathParam("id") String id)
	{
		return userServices.findTechnicalUserById(id);
	}

	@Override
	@GET
	@Path(TECHUSER_PER_NUMBER_RESOURCE_PATH + "{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public IAuthorizableTechUser findTechnicalUserByNumber(@PathParam("number") String number)
	{
		return userServices.findTechnicalUserByNumber(number);
	}

	@Override
	@GET
	@Path(ALL_TECHUSERS_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public Set<IAuthorizableTechUser> obtainAllTechnicalUsers()
	{
		return userServices.obtainAllTechnicalUsers();
	}
}
