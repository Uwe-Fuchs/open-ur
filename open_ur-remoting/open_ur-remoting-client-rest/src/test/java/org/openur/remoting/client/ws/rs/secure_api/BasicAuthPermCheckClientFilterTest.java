package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

public class BasicAuthPermCheckClientFilterTest
	extends AbstractSecurityClientFilterTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(CheckIfSecurityIsPresentInRequestFilter.class);

		return config;
	}

	@Test
	public void testFilter()
	{
		AuthenticationClientFilter_BasicAuth authenticationFilter = new AuthenticationClientFilter_BasicAuth(TEST_USER_NAME, TEST_PASSWORD);
		AuthorizationClientFilter authorization = new AuthorizationClientFilter(TEST_APPLICATION_NAME);
		
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder(authenticationFilter, authorization);
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		String msg = response.readEntity(String.class);
		assertEquals(msg, CheckIfAuthenticationIsPresentInRequestFilter.AUTHENTICATION_FOUND_MSG + CheckIfAuthorizationIsPresentInRequest.AUTHORIZATION_FOUND_MSG);
	}
}
