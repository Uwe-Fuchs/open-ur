package org.openur.remoting.resource.errorhandling;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper
	extends AbstractExceptionMapper<Throwable>
	implements ExceptionMapper<Throwable>
{
}
