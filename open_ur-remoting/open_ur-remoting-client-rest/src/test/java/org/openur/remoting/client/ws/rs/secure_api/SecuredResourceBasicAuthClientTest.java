package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.assertEquals;
import static org.openur.remoting.client.ws.rs.secure_api.checkfilters.AbstractCheckIfSecurityIsPresentFilter.*;
import static org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfAuthenticationIsPresentInRequestFilter.AUTHENTICATION_FOUND_MSG;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfAuthenticationIsPresentInRequestFilter;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;

public class SecuredResourceBasicAuthClientTest
	extends AbstractSecuredResourceClientTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(new CheckIfAuthenticationIsPresentInRequestFilter(ResponseStatus.status_444));
		config.register(ErrorMessageProvider.class);

		return config;
	}
	
	@Test
	public void testFilter()
		throws EntityNotFoundException
	{
		try
		{
			userBean.doSomeUserAction();
		} catch (WebApplicationException e)
		{
			assertEquals(444, e.getResponse().getStatus());	
			assertEquals(AUTHENTICATION_FOUND_MSG, e.getMessage());
		}
	}
}
