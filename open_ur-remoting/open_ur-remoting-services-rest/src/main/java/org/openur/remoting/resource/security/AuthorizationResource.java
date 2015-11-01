package org.openur.remoting.resource.security;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.openur.module.service.security.IAuthorizationServices;

@Path(AuthorizationResource.AUTHORIZATION_RESOURCE_PATH)
public class AuthorizationResource
	implements IAuthorizationServices
{
	public static final String AUTHORIZATION_RESOURCE_PATH = "authorization/";
	public static final String HAS_OU_PERMISSION_RESOURCE_PATH = "ou";
	public static final String HAS_SYSTEM_PERMISSION_RESOURCE_PATH = "system";
	
	@Inject
	IAuthorizationServices authorizationServices;

	@Override
	@GET
	@Path(HAS_OU_PERMISSION_RESOURCE_PATH)
	@Produces( MediaType.TEXT_PLAIN )
	public Boolean hasPermission(@QueryParam("personId") String personId, @QueryParam("ouId") String orgUnitId, 
		@QueryParam("text") String permissionText, @QueryParam("appName") String applicationName)
	{
		return authorizationServices.hasPermission(personId, orgUnitId, permissionText, applicationName);
	}

	@Override
	@GET
	@Path(HAS_SYSTEM_PERMISSION_RESOURCE_PATH)
	@Produces( MediaType.TEXT_PLAIN )
	public Boolean hasPermission(@QueryParam("personId") String personId, @QueryParam("text") String permissionText, 
		@QueryParam("appName") String applicationName)
	{
		return authorizationServices.hasPermission(personId, permissionText, applicationName);
	}	
}
