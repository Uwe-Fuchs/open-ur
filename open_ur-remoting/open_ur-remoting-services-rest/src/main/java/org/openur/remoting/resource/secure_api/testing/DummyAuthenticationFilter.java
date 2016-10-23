package org.openur.remoting.resource.secure_api.testing;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

@Provider
@Priority(value = Priorities.AUTHENTICATION)
public class DummyAuthenticationFilter
	implements ContainerRequestFilter
{
	private boolean addUserIdToContext = true;

	public void setAddUserIdToContext(boolean addUserIdToContext)
	{
		this.addUserIdToContext = addUserIdToContext;
	}

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		if (!addUserIdToContext)
		{
			return;
		}
			
		requestContext.setProperty(AbstractSecurityFilterBase.USER_ID_PROPERTY, OpenUrRdbmsRealmMock.TECH_USER_UUID_2);
	}
}
