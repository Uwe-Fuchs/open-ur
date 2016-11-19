package org.openur.remoting.client.ws.rs.secure_api;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.grizzly.http.server.GrizzlyPrincipal;
import org.openur.remoting.resource.secure_api.OpenUrSecurityContext;

@Priority(value = Priorities.AUTHENTICATION - 20)
public class DummyPreparePreAuthFilter
	implements ContainerRequestFilter
{
	private final String userId;	
	private boolean securityContextInRequestContext = true;
	
	public DummyPreparePreAuthFilter(String userId)
	{
		super();
		
		this.userId = userId;
	}

	public void setSecurityContextInRequestContext(boolean securityContextInRequestContext)
	{
		this.securityContextInRequestContext = securityContextInRequestContext;
	}

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		if (!securityContextInRequestContext)
		{
			return;
		}
		
		Principal principal = new GrizzlyPrincipal(this.userId);
		SecurityContext securityContext = new OpenUrSecurityContext(principal);
		requestContext.setSecurityContext(securityContext);
	};
}
