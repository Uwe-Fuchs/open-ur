package org.openur.remoting.resource.security;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.openur.module.service.security.IAuthorizationServices;

@Path("/authorization")
public class AuthorizationResource
	implements IAuthorizationServices
{
	@Inject
	IAuthorizationServices authorizationServices;

	@Override
	@GET
	@Path("/ou")
	@Produces( MediaType.TEXT_PLAIN )
	public Boolean hasPermission(@QueryParam("personId") String personId, @QueryParam("ouId") String orgUnitId, 
		@QueryParam("text") String permissionText, @QueryParam("appName") String applicationName)
	{
		return authorizationServices.hasPermission(personId, orgUnitId, permissionText, applicationName);
	}

	@Override
	@GET
	@Path("/system")
	@Produces( MediaType.TEXT_PLAIN )
	public Boolean hasPermission(@QueryParam("personId") String personId, @QueryParam("text") String permissionText, 
		@QueryParam("appName") String applicationName)
	{
		return authorizationServices.hasPermission(personId, permissionText, applicationName);
	}	
}
