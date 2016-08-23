package org.openur.remoting.client.ws.rs.security;

import static org.openur.remoting.resource.security.AuthorizationResource.HAS_OU_PERMISSION_RESOURCE_PATH;
import static org.openur.remoting.resource.security.AuthorizationResource.HAS_SYSTEM_PERMISSION_RESOURCE_PATH;
import static org.openur.remoting.resource.security.AuthorizationResource.HAS_TECH_USER_PERMISSION_RESOURCE_PATH;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.client.ws.rs.secure_api.AbstractResourceClient;
import org.openur.remoting.resource.security.AuthorizationResource;
import org.openur.remoting.xchange.rest.providers.json.PermissionProvider;

public class AuthorizationResourceClient
	extends AbstractResourceClient
	implements IAuthorizationServices
{
	@Inject
	public AuthorizationResourceClient(String baseUrl)
	{
		super(baseUrl + AuthorizationResource.AUTHORIZATION_RESOURCE_PATH, PermissionProvider.class);
	}

	@Override
	public Boolean hasPermission(String personId, String orgUnitId, String permissionText, String applicationName) 
		throws EntityNotFoundException
	{
		String url = new StringBuilder()
				.append(HAS_OU_PERMISSION_RESOURCE_PATH)
				.append("?personId=")
				.append(personId)
				.append("&ouId=")
				.append(orgUnitId)
				.append("&text=")
				.append(permissionText)
				.append("&appName=")
				.append(applicationName)
				.toString();
		
		return performRestCall(url, MediaType.TEXT_PLAIN);
	}

	@Override
	public Boolean hasPermission(String personId, String permissionText, String applicationName) 
		throws EntityNotFoundException
	{
		String url = new StringBuilder()
				.append(HAS_SYSTEM_PERMISSION_RESOURCE_PATH)
				.append("?personId=")
				.append(personId)
				.append("&text=")
				.append(permissionText)
				.append("&appName=")
				.append(applicationName)
				.toString();
		
		return performRestCall(url, MediaType.TEXT_PLAIN);
	}

	@Override
	public Boolean hasPermissionTechUser(String techUserId, String permissionText, String applicationName) 
		throws EntityNotFoundException
	{
		String url = new StringBuilder()
				.append(HAS_TECH_USER_PERMISSION_RESOURCE_PATH)
				.append("?techUserId=")
				.append(techUserId)
				.append("&text=")
				.append(permissionText)
				.append("&appName=")
				.append(applicationName)
				.toString();
		
		return performRestCall(url, MediaType.TEXT_PLAIN);
	}

	private Boolean performRestCall(String url, String acceptMediaType)
		throws EntityNotFoundException
	{
		try
		{
			return super.performRestCall_GET(url, MediaType.TEXT_PLAIN, Boolean.class);			
		} catch (WebApplicationException e)
		{
			Throwable cause = e.getCause();
			
			if (cause != null && EntityNotFoundException.class.equals(cause.getClass()))
			{
				throw (EntityNotFoundException) cause;
			}

			throw e;
		}
	}
}
