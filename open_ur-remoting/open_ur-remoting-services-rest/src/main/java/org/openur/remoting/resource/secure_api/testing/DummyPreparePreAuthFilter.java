package org.openur.remoting.resource.secure_api.testing;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.grizzly.http.server.GrizzlyPrincipal;

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
		SecurityContext securityContext = new MySecurityContext(principal);
		requestContext.setSecurityContext(securityContext);
	}

	public static final class MySecurityContext
		implements SecurityContext
	{
		private final Principal principal;
		
		public MySecurityContext(Principal principal)
		{
			this.principal = principal;
		}

		@Override
		public boolean isUserInRole(final String role)
		{
			return false;
		}

		@Override
		public boolean isSecure()
		{
			return false;
		}

		@Override
		public Principal getUserPrincipal()
		{
			return this.principal;
		}

		@Override
		public String getAuthenticationScheme()
		{
			return null;
		}
	};
}
