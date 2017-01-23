package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.assertEquals;
import static org.openur.remoting.client.ws.rs.secure_api.checkfilters.AbstractCheckIfSecurityIsPresentFilter.*;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfAuthenticationIsPresentInRequestFilter;

public class BasicAuthClientFilterTest
	extends AbstractSecurityClientFilterTest
{	
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(new CheckIfAuthenticationIsPresentInRequestFilter(ResponseStatus.status_200));

		return config;
	}

	@Test
	public void testFilter()
	{
		AuthenticationClientFilter_BasicAuth testFilter = new AuthenticationClientFilter_BasicAuth(TEST_USER_NAME, TEST_PASSWORD);
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder(testFilter);
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		String msg = response.readEntity(String.class);
		assertEquals(msg, CheckIfAuthenticationIsPresentInRequestFilter.AUTHENTICATION_FOUND_MSG);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFilterEmptyUserName()
	{
		new AuthenticationClientFilter_BasicAuth("", "");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFilterEmptyPassword()
	{
		new AuthenticationClientFilter_BasicAuth("someUsername", "");
	}
}
