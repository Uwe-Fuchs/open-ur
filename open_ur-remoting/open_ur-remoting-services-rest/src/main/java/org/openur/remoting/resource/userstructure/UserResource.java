package org.openur.remoting.resource.userstructure;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.service.userstructure.IUserServices;

@Path("/userstructure")
public class UserResource
	implements IUserServices
{
	@Inject
	private IUserServices userServices;

	@GET
	@Path("/person/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IPerson findPersonById(@PathParam("id") String id)
	{
		return userServices.findPersonById(id);
	}

	@GET
	@Path("/person/number/{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IPerson findPersonByNumber(@PathParam("number") String number)
	{
		return userServices.findPersonByNumber(number);
	}

	@GET
	@Path("/person/all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IPerson> obtainAllPersons()
	{
		return userServices.obtainAllPersons();
	}

	@GET
	@Path("/techuser/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ITechnicalUser findTechnicalUserById(@PathParam("id") String id)
	{
		return userServices.findTechnicalUserById(id);
	}

	@GET
	@Path("/techuser/number/{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ITechnicalUser findTechnicalUserByNumber(@PathParam("number") String number)
	{
		return userServices.findTechnicalUserByNumber(number);
	}

	@GET
	@Path("/techuser/all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<ITechnicalUser> obtainAllTechnicalUsers()
	{
		return userServices.obtainAllTechnicalUsers();
	}
}
