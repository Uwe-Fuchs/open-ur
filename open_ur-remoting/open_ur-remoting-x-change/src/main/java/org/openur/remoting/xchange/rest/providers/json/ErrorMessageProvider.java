package org.openur.remoting.xchange.rest.providers.json;

import org.openur.remoting.xchange.rest.errorhandling.ErrorMessage;

public class ErrorMessageProvider
	extends AbstractProvider<ErrorMessage>
{
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return ErrorMessage.class.isAssignableFrom(type);
	}	
}
