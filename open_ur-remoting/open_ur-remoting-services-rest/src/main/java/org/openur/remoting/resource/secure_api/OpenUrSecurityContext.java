package org.openur.remoting.resource.secure_api;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public final class OpenUrSecurityContext
	implements SecurityContext
{
	private final Principal principal;
	
	public OpenUrSecurityContext(Principal principal)
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
}