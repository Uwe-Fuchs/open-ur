package org.openur.remoting.resource.userstructure;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.service.userstructure.IUserServices;

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

		return null;
	}

	@GET
	@Path("/number/{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPersonByNumber(@PathParam("number") String number)
	{
		IPerson p = userServices.findPersonByNumber(number);

		return null;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt()
	{
		IPerson p = userServices.findPersonById("123");

		return "Got it!";
	}
}
