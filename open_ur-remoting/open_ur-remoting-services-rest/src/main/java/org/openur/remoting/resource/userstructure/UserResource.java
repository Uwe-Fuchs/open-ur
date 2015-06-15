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
import org.openur.module.service.userstructure.IUserServices;

import com.google.gson.Gson;

@Path("/userstructure")
public class UserResource
{
	@Inject
	private IUserServices userServices;

	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPersonById(@PathParam("id") String id)
	{
		IPerson p = userServices.findPersonById(id);
		
		return Response.status(Response.Status.OK)
				.entity(new Gson().toJson(p))
				.build();
	}

	@GET
	@Path("/number/{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPersonByNumber(@PathParam("number") String number)
	{
		IPerson p = userServices.findPersonByNumber(number);
		
		return Response.status(Response.Status.OK)
				.entity(new Gson().toJson(p))
				.build();
	}

	@GET
	@Path("/allPersons")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response obtainAllPersons()
	{
		Set<IPerson> allPersons = userServices.obtainAllPersons();
		
		return Response.status(Response.Status.OK)
				.entity(new Gson().toJson(allPersons, Set.class))
				.build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt()
	{
		return "Got it!";
	}
}
