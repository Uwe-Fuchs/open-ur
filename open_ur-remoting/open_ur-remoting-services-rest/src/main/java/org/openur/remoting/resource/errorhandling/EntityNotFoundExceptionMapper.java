package org.openur.remoting.resource.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.openur.module.util.exception.EntityNotFoundException;

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
