package org.openur.remoting.resource.errorhandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang3.StringUtils;
import org.openur.remoting.xchange.rest.errorhandling.ErrorMessage;

public abstract class AbstractExceptionMapper<T extends Throwable>
	implements ExceptionMapper<T>
{
	@Override
	public Response toResponse(T exception)
	{
		String message = StringUtils.defaultString(exception.getMessage(), getDefaultExceptionMessage());
		Status status = getHttpStatus();
		ErrorMessage errorMessage = new ErrorMessage(message, exception.getClass(), status);
		
		return Response
				.status(status)
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
	
	protected String getDefaultExceptionMessage()
	{
		return "An exception has occurred!";
	}
	
	protected Status getHttpStatus()
	{
		return Status.INTERNAL_SERVER_ERROR;
	}
}
