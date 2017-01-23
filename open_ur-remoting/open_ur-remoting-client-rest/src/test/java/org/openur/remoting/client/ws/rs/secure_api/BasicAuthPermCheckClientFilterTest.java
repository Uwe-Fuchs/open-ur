package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.assertEquals;
import static org.openur.remoting.client.ws.rs.secure_api.checkfilters.AbstractCheckIfSecurityIsPresentFilter.ResponseStatus;
import static org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfAuthenticationIsPresentInRequestFilter.AUTHENTICATION_FOUND_MSG;
import static org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfAuthorizationIsPresentInRequest.AUTHORIZATION_FOUND_MSG;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfSecurityIsPresentInRequestFilter;

public class BasicAuthPermCheckClientFilterTest
	extends AbstractSecurityClientFilterTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(new CheckIfSecurityIsPresentInRequestFilter(ResponseStatus.status_200));

		return config;
	}

	@Test
	public void testFilter()
	{
		AuthenticationClientFilter_BasicAuth authenticationFilter = new AuthenticationClientFilter_BasicAuth(TEST_USER_NAME, TEST_PASSWORD);
		AuthorizationClientFilter authorizationFilter = new AuthorizationClientFilter(TEST_APPLICATION_NAME);
		
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder(authenticationFilter, authorizationFilter);
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		String msg = response.readEntity(String.class);
		assertEquals(AUTHENTICATION_FOUND_MSG + AUTHORIZATION_FOUND_MSG, msg);
	}
}
