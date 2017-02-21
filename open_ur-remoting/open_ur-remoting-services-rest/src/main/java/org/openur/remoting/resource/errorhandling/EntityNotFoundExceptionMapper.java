package org.openur.remoting.resource.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.openur.module.util.exception.EntityNotFoundException;

@Provider
public class EntityNotFoundExceptionMapper
	extends AbstractExceptionMapper<EntityNotFoundException>
{
	@Override
	protected String getDefaultExceptionMessage()
	{
		return "Entity not found!";
	}

	@Override
	protected Status getHttpStatus(Throwable ex)
	{
		return Response.Status.NOT_FOUND;
	}
}
