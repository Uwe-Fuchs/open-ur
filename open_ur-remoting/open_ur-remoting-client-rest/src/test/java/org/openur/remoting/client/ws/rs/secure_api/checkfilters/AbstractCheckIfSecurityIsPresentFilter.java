package org.openur.remoting.client.ws.rs.secure_api.checkfilters;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.openur.remoting.xchange.rest.errorhandling.ErrorMessage;

public abstract class AbstractCheckIfSecurityIsPresentFilter
	implements ContainerRequestFilter
{
	private static String successMessage;

	protected final Response badRequestResponse = Response
			.status(Response.Status.BAD_REQUEST)
			.build();
		
	protected final ResponseStatus responseStatus;	
	
	protected static void setSuccessMessage(String successMessage)
	{
		AbstractCheckIfSecurityIsPresentFilter.successMessage = successMessage;
	}

	public AbstractCheckIfSecurityIsPresentFilter(AbstractCheckIfSecurityIsPresentFilter.ResponseStatus responseStatus)
	{
		super();
		
		this.responseStatus = responseStatus;
	}

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		doFilter(requestContext);
		
		Response okResponse = Response
			.status(Response.Status.OK)
			.entity(successMessage)
			.build();
		
		Response noResponse = Response
				.status(444)
				.entity(new ErrorMessage(successMessage, WebApplicationException.class, Response.Status.NOT_FOUND))
				.build();
		
		requestContext.abortWith(responseStatus == ResponseStatus.status_200 ? okResponse : noResponse);
	}
	
	protected abstract void doFilter(ContainerRequestContext requestContext)
		throws IOException;
	
	public static enum ResponseStatus 
	{
		status_200, status_444
	}
}
