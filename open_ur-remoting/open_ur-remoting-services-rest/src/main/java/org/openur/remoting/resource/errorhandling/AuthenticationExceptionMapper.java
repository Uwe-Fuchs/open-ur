package org.openur.remoting.resource.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.authc.AuthenticationException;

@Provider
public class AuthenticationExceptionMapper
	extends AbstractExceptionMapper<AuthenticationException>
{
	@Override
	protected String getDefaultExceptionMessage()
	{
		return "Authentication failed!";
	}

	@Override
	protected Status getHttpStatus(Throwable ex)
	{
		return Response.Status.FORBIDDEN;
	}
}
