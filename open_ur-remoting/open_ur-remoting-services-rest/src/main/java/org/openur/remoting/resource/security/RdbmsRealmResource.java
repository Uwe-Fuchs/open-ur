package org.openur.remoting.resource.security;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.openur.module.service.security.realm.rdbms.OpenUrRdbmsRealm;

@Path(RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH)
public class RdbmsRealmResource
	implements Realm
{
	public static final String RDBMS_REALM_RESOURCE_PATH = "rdbmsRealm/";
	public static final String AUTHENTICATE_RESOURCE_PATH = "getAuthenticationInfo";
	public static final String SUPPORTS_RESOURCE_PATH = "supports";
	public static final String GET_NAME_RESOURCE_PATH = "getName";

	@Inject
	private OpenUrRdbmsRealm realm;

	@Override
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(GET_NAME_RESOURCE_PATH)
	public String getName()
	{
		return realm.getName();
	}

	@Override
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	@Path(SUPPORTS_RESOURCE_PATH)
	public boolean supports(AuthenticationToken token)
	{
		return realm.supports(token);
	}

	@Override
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path(AUTHENTICATE_RESOURCE_PATH)
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		return realm.getAuthenticationInfo(token);
	}
}
