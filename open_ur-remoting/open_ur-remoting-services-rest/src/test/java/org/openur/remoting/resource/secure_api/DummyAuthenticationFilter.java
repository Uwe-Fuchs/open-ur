package org.openur.remoting.resource.secure_api;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.grizzly.http.server.GrizzlyPrincipal;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.remoting.resource.secure_api.OpenUrSecurityContext;

@Provider
@Priority(value = Priorities.AUTHENTICATION)
public class DummyAuthenticationFilter
	implements ContainerRequestFilter
{
	private boolean userIdInContext = true;

	public void setUserIdInContext(boolean userIdInContext)
	{
		this.userIdInContext = userIdInContext;
	}

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		if (!userIdInContext)
		{
			return;
		}
		
		Principal principal = new GrizzlyPrincipal(OpenUrRdbmsRealmMock.TECH_USER_UUID_2);
		SecurityContext securityContext = new OpenUrSecurityContext(principal);
		requestContext.setSecurityContext(securityContext);
	}
}
