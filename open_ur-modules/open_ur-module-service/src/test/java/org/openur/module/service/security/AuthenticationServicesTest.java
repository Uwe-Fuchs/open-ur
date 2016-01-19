package org.openur.module.service.security;

import javax.inject.Inject;

import org.apache.shiro.realm.Realm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.security.authentication.UsernamePasswordToken;
import org.openur.module.service.config.SecurityTestSpringConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testSecurityServices")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class AuthenticationServicesTest
{
	private static final String USER_NAME = "testUser";
	private static final String PASSWORD = "secret";
	
	@Inject
	private IAuthenticationServices authenticationServices;
	
	@Inject
	private Realm realm;
	
	@Test
	public void testAuthenticate()
	{
		UsernamePasswordToken openUrToken = new UsernamePasswordToken(USER_NAME, PASSWORD);
		authenticationServices.authenticate(openUrToken);
	}
	
	@Test(expected=org.openur.module.util.exception.AuthenticationException.class)
	public void testAuthenticate_Failure()
	{
		UsernamePasswordToken openUrToken = new UsernamePasswordToken(USER_NAME, "someWrongPw");
		Mockito.when(realm.getAuthenticationInfo(openUrToken.getDelegate())).thenThrow(
				new org.apache.shiro.authc.AuthenticationException("wrong credentials"));
		authenticationServices.authenticate(openUrToken);		
	}
}
