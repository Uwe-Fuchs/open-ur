package org.openur.remoting.resource.errorhandling;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper
	extends AbstractExceptionMapper<Throwable>
	implements ExceptionMapper<Throwable>
{
	@Override
	protected Status getHttpStatus(Throwable ex)
	{
		if (ex instanceof WebApplicationException)
		{
			int statusCode = ((WebApplicationException) ex).getResponse().getStatus();
			
			return Status.fromStatusCode(statusCode);
		}
		
		return super.getHttpStatus(ex);
	}
}
