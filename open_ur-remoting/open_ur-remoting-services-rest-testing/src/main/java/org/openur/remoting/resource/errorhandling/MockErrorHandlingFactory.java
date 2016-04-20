package org.openur.remoting.resource.errorhandling;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.glassfish.hk2.api.Factory;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;
import org.openur.module.util.exception.OpenURRuntimeException;
import org.openur.remoting.resource.security.MockRdbmsRealmFactory.OpenUrRdbmsRealmMock;

public class MockErrorHandlingFactory
	implements Factory<OpenUrRdbmsRealm>
{
	@Override
	public OpenUrRdbmsRealm provide()
	{
		return new ErrorHandlingTestRealmMock();
	}

	@Override
	public void dispose(OpenUrRdbmsRealm userServices)
	{
	}
	
	public static class ErrorHandlingTestRealmMock
		extends OpenUrRdbmsRealm
	{
		public static final String RUNTIME_ERROR_MSG = "Common OpenURRuntimeException!";
		
		@Override
		protected UsernamePwAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException
		{
			if (EqualsBuilder.reflectionEquals(OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW, token))
			{
				throw new AuthenticationException(OpenUrRdbmsRealmMock.ERROR_MSG);
			}
			
			if (EqualsBuilder.reflectionEquals(OpenUrRdbmsRealmMock.TOKEN_WITH_UNKNOWN_USERNAME, token))
			{
				throw new OpenURRuntimeException(RUNTIME_ERROR_MSG);
			}

			return null;
		}		
	}
}
