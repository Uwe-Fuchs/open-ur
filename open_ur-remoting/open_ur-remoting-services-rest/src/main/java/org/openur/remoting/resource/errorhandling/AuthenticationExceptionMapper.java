package org.openur.remoting.resource.errorhandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.shiro.authc.AuthenticationException;
import org.openur.remoting.xchange.rest.errorhandling.ErrorMessage;

@Provider
public class AuthenticationExceptionMapper
	implements ExceptionMapper<AuthenticationException>
{
	@Override
	public Response toResponse(AuthenticationException exception)
	{
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), AuthenticationException.class, Response.Status.NOT_FOUND);
		
		return Response
				.status(Response.Status.NOT_FOUND)
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
