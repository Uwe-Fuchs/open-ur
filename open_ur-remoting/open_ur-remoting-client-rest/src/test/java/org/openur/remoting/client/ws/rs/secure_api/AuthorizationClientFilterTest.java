package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.assertEquals;
import static org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfAuthorizationIsPresentInRequest.AUTHORIZATION_FOUND_MSG;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfAuthorizationIsPresentInRequest;
import org.openur.remoting.client.ws.rs.secure_api.checkfilters.AbstractCheckIfSecurityIsPresentFilter.ResponseStatus;

public class AuthorizationClientFilterTest
	extends AbstractSecurityClientFilterTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(new CheckIfAuthorizationIsPresentInRequest(ResponseStatus.status_200));
		
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
		assertEquals(msg, AUTHORIZATION_FOUND_MSG);
	}

	@Test(expected = NullPointerException.class)
	public void testFilterEmptyApplicationName()
	{
		new AuthorizationClientFilter(null);
	}
}
