package org.openur.remoting.resource.security;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.util.exception.EntityNotFoundException;

@Path(AuthorizationResource.AUTHORIZATION_RESOURCE_PATH)
public class AuthorizationResource
	implements IAuthorizationServices
{
	public static final String AUTHORIZATION_RESOURCE_PATH = "authorization/";
	public static final String HAS_OU_PERMISSION_RESOURCE_PATH = "ou";
	public static final String HAS_SYSTEM_PERMISSION_RESOURCE_PATH = "system";
	public static final String HAS_TECH_USER_PERMISSION_RESOURCE_PATH = "techuser";

	@Inject
	private IAuthorizationServices authorizationServices;

	@Override
	@GET
	@Path(HAS_OU_PERMISSION_RESOURCE_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean hasPermission(@QueryParam("personId") String personId, @QueryParam("ouId") String orgUnitId, 
			@QueryParam("text") String permissionText, @QueryParam("appName") String applicationName)
		throws EntityNotFoundException
	{
		try
		{
			return authorizationServices.hasPermission(personId, orgUnitId, permissionText, applicationName);
		} catch (EntityNotFoundException e)
		{
			throw new WebApplicationException(e.getMessage(), e, Status.BAD_REQUEST);
		}
	}

	@Override
	@GET
	@Path(HAS_SYSTEM_PERMISSION_RESOURCE_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean hasPermission(@QueryParam("personId") String personId, @QueryParam("text") String permissionText, 
			@QueryParam("appName") String applicationName)
		throws EntityNotFoundException
	{
		return authorizationServices.hasPermission(personId, permissionText, applicationName);
	}

	@Override
	@GET
	@Path(HAS_TECH_USER_PERMISSION_RESOURCE_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public Boolean hasPermissionTechUser(@QueryParam("techUserId") String techUserId, @QueryParam("text") String permissionText, 
			@QueryParam("appName") String applicationName)
		throws EntityNotFoundException
	{
		return authorizationServices.hasPermissionTechUser(techUserId, permissionText, applicationName);
	}
}
