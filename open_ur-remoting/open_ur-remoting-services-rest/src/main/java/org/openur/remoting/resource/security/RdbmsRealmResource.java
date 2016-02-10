package org.openur.remoting.resource.security;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path(RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH)
public class RdbmsRealmResource
// implements Realm
{
	public static final String RDBMS_REALM_RESOURCE_PATH = "rdbmsRealm/";
	public static final String AUTHENTICATE_RESOURCE_PATH = "getAuthenticationInfo";
	public static final String SUPPORTS_RESOURCE_PATH = "supports";
	public static final String GET_NAME_RESOURCE_PATH = "getName";

	@Inject
	private Realm realm;

	// @Override
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(GET_NAME_RESOURCE_PATH)
	public String getName(@QueryParam("name") String name)
	{
		// return realm.getName();
		return name;
	}

	// @Override
	// @POST
	// @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	// @Produces( MediaType.TEXT_PLAIN )
	// @Path(RDBMS_REALM_RESOURCE_PATH + SUPPORTS_RESOURCE_PATH)
	// public boolean supports(AuthenticationToken token)
	// {
	// return realm.supports(token);
	// }

	// @Override
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path(AUTHENTICATE_RESOURCE_PATH)
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		return realm.getAuthenticationInfo(token);
	}

//	@POST
//	@Consumes(MediaType.TEXT_PLAIN)
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path(AUTHENTICATE_RESOURCE_PATH)
//	public UsernamePasswordToken getAuthenticationInfo(String token)
//		throws AuthenticationException
//	{
//		Gson gson = new GsonBuilder().create();
//		return gson.fromJson(token, UsernamePasswordToken.class);
//	}
}
