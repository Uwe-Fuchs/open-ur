package org.openur.remoting.resource.userstructure;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.service.userstructure.IUserServices;

@Path("/userstructure")
public class UserResource
{
	@Inject
	private IUserServices userServices;

	@GET
	@Path("/person/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPersonById(@PathParam("id") String id)
	{
		IPerson p = userServices.findPersonById(id);
		
		return Response.status(Response.Status.OK)
				.entity(p)
				.build();
	}

	@GET
	@Path("/person/number/{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPersonByNumber(@PathParam("number") String number)
	{
		IPerson p = userServices.findPersonByNumber(number);
		
		return Response.status(Response.Status.OK)
				.entity(p)
				.build();
	}

	@GET
	@Path("/person/all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response obtainAllPersons()
	{
		Set<IPerson> allPersons = userServices.obtainAllPersons();
		
		return Response.status(Response.Status.OK)
				.entity(allPersons)
				.build();
	}

	@GET
	@Path("/techuser/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findTechnicalUserById(@PathParam("id") String id)
	{
		ITechnicalUser tu = userServices.findTechnicalUserById(id);
		
		return Response.status(Response.Status.OK)
				.entity(tu)
				.build();
	}

	@GET
	@Path("/techuser/number/{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findTechnicalUserByNumber(@PathParam("number") String number)
	{
		ITechnicalUser tu = userServices.findTechnicalUserByNumber(number);
		
		return Response.status(Response.Status.OK)
				.entity(tu)
				.build();
	}

	@GET
	@Path("/techuser/all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response obtainAllTechnicalUsers()
	{
		Set<ITechnicalUser> techUsersSet = userServices.obtainAllTechnicalUsers();
		
		return Response.status(Response.Status.OK)
				.entity(techUsersSet)
				.build();
	}
}
