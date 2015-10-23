package org.openur.remoting.resource.userstructure;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.service.userstructure.IUserServices;

@Path(IUserResource.USER_RESOURCE_PATH)
public interface IUserResource
	extends IUserServices
{
	public static final String USER_RESOURCE_PATH = "userstructure/";
	public static final String PERSON_PER_ID_RESOURCE_PATH = "person/id/";
	public static final String PERSON_PER_NUMBER_RESOURCE_PATH = "person/number/";
	public static final String ALL_PERSONS_RESOURCE_PATH = "person/all";
	public static final String TECHUSER_PER_ID_RESOURCE_PATH = "techuser/id/";
	public static final String TECHUSER_PER_NUMBER_RESOURCE_PATH = "techuser/number/";
	public static final String ALL_TECHUSERS_RESOURCE_PATH = "techuser/all";

	@GET
	@Path(PERSON_PER_ID_RESOURCE_PATH + "{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	IPerson findPersonById(@PathParam("id") String id);

	@GET
	@Path(PERSON_PER_NUMBER_RESOURCE_PATH + "{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	IPerson findPersonByNumber(@PathParam("number") String number);

	@GET
	@Path(ALL_PERSONS_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Set<IPerson> obtainAllPersons();

	@GET
	@Path(TECHUSER_PER_ID_RESOURCE_PATH + "{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	ITechnicalUser findTechnicalUserById(@PathParam("id") String id);

	@GET
	@Path(TECHUSER_PER_NUMBER_RESOURCE_PATH + "{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	ITechnicalUser findTechnicalUserByNumber(@PathParam("number") String number);

	@GET
	@Path(ALL_TECHUSERS_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Set<ITechnicalUser> obtainAllTechnicalUsers();
}