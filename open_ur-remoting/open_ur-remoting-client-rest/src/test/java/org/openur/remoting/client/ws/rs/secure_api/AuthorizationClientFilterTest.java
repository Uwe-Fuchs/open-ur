package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.module.util.exception.EntityNotFoundException;

public class AuthorizationClientFilterTest
	extends AbstractSecurityClientFilterTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(CheckIfAuthorizationIsPresentInRequest.class);
		
		return config;
	}

	@Test
	public void testFilterValidAuthorization()
		throws EntityNotFoundException
	{
		AuthorizationClientFilter testFilter = new AuthorizationClientFilter(TEST_APPLICATION_NAME);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder(testFilter);
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		String msg = response.readEntity(String.class);
		assertEquals(msg, CheckIfAuthorizationIsPresentInRequest.AUTHORIZATION_FOUND_MSG);
	}

	@Test(expected = NullPointerException.class)
	public void testFilterEmptyApplicationName()
	{
		new AuthorizationClientFilter(null);
	}
}
