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
	private static final String GENERIC_DEFAULT_ERROR_MSG = "An exception has occurred!";
	
	@Override
	public Response toResponse(T exception)
	{
		String message = StringUtils.defaultString(exception.getMessage(), getDefaultExceptionMessage());
		Status status = getHttpStatus(exception);
		ErrorMessage errorMessage = new ErrorMessage(message, exception.getClass(), status);
		
		Response response = Response
				.status(status)
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
		
		return response;
	}
	
	public static String getGenericDefaultErrorMsg()
	{
		return GENERIC_DEFAULT_ERROR_MSG;
	}

	protected String getDefaultExceptionMessage()
	{
		return getGenericDefaultErrorMsg();
	}
	
	protected Status getHttpStatus(Throwable ex)
	{
		return Status.INTERNAL_SERVER_ERROR;
	}
}
