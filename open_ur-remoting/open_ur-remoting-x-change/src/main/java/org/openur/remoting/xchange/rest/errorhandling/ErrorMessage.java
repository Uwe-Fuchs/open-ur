package org.openur.remoting.xchange.rest.errorhandling;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 
 * @author uwe
 */
public class ErrorMessage
{
	private final String message;
	private final String exceptionClassName;
	private final Status status;
	
	public ErrorMessage(String message, Class<? extends Throwable> exceptionClass, Status status)
	{
		super();
		
		Validate.notNull(exceptionClass, "exception-class must not be null!");
		Validate.notNull(status, "status must not be null!");
		
		this.message = StringUtils.defaultString(message, "");
		this.exceptionClassName = exceptionClass.getCanonicalName();
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public String getExceptionClassName()
	{
		return exceptionClassName;
	}

	public Status getStatus()
	{
		return status;
	}
}
