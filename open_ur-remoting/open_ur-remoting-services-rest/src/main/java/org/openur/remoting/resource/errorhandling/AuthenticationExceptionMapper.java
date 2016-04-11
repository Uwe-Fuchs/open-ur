package org.openur.remoting.resource.errorhandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.authc.AuthenticationException;

@Provider
public class AuthenticationExceptionMapper
	implements ExceptionMapper<AuthenticationException>
{
	@Override
	public Response toResponse(AuthenticationException exception)
	{
		return Response
				.status(Response.Status.NOT_FOUND)
				.entity(exception.getMessage())
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
