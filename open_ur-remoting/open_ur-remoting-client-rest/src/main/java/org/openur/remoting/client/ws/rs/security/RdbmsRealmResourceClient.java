package org.openur.remoting.client.ws.rs.security;

import static org.openur.remoting.resource.security.RdbmsRealmResource.AUTHENTICATE_RESOURCE_PATH;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.openur.remoting.resource.client.AbstractResourceClient;
import org.openur.remoting.resource.security.RdbmsRealmResource;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwAuthenticationInfoProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

public class RdbmsRealmResourceClient
	extends AbstractResourceClient
	implements Realm
{
	public RdbmsRealmResourceClient(String baseUrl)
	{
		super(baseUrl + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH, UsernamePwTokenProvider.class, UsernamePwAuthenticationInfoProvider.class);
	}

	@Override
	public String getName()
	{
		return performRestCall_GET(RdbmsRealmResource.GET_NAME_RESOURCE_PATH, MediaType.TEXT_PLAIN, String.class);
	}

	@Override
	public boolean supports(AuthenticationToken token)
	{
		return performRestCall(
				RdbmsRealmResource.SUPPORTS_RESOURCE_PATH, HttpMethod.PUT, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, Boolean.class, token);
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		try
		{
			return performRestCall(
					AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, AuthenticationInfo.class, token);			
		} catch (WebApplicationException e)
		{
			if (e.getCause() != null && AuthenticationException.class.equals(e.getCause().getClass()))
			{
				throw (AuthenticationException) e.getCause();
			}
			
			throw e;
		}
	}
}
