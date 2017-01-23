package org.openur.module.integration.security.shiro;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.integration.security.shiro.config.SecurityTestSpringConfig;
import org.openur.module.integration.security.shiro.testing.OpenUrRdbmsRealmMock;
import org.openur.module.service.security.IAuthenticationServices;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testSecurityServices")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class AuthenticationServicesTest
{
	@Inject
	private IAuthenticationServices authenticationServices;
	
	@Test
	public void testAuthenticate()
	{
		authenticationServices.authenticate(OpenUrRdbmsRealmMock.USER_NAME_1, OpenUrRdbmsRealmMock.PASSWORD_1);
	}
	
	@Test(expected=org.openur.module.util.exception.AuthenticationException.class)
	public void testAuthenticate_Failure()
	{
		authenticationServices.authenticate(OpenUrRdbmsRealmMock.USER_NAME_1, "someWrongPw");
	}
}
