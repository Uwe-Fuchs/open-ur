package org.openur.module.service.config;

import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.service.security.AuthenticationServicesImpl;
import org.openur.module.service.security.AuthorizationServicesImpl;
import org.openur.module.service.security.IAuthenticationServices;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.module.service.security.SecurityDomainServicesImpl;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value="testSecurityServices")
public class SecurityTestSpringConfig
{
	@Bean(name = "authenticationServices")
	public IAuthenticationServices authenticationServices()
	{
		return new AuthenticationServicesImpl();
	}

	@Bean(name = "realmMock")
	public OpenUrRdbmsRealm realm()
	{
		return new OpenUrRdbmsRealmMock();
	}
	
	@Bean(name = "securityDomainServices")
	public ISecurityDomainServices securityDomainServices()
	{
		return new SecurityDomainServicesImpl();
	}

	@Bean(name = "authorizationServices")
	public IAuthorizationServices authorizationServices()
	{
		return new AuthorizationServicesImpl();
	}

	@Bean(name = "securityDaoMock")
	public ISecurityDao securityDao()
	{		
		return mock(ISecurityDao.class);
	}

	@Bean(name = "userServicesMock")
	public IUserServices userServices()
	{		
		return mock(IUserServices.class);
	}

	@Bean(name = "orgUnitServicesMock")
	public IOrgUnitServices orgUnitServices()
	{		
		return mock(IOrgUnitServices.class);
	}
	
	static class OpenUrRdbmsRealmMock
		extends OpenUrRdbmsRealm
	{
		public static final UsernamePasswordToken USERNAME_PW_TOKEN = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1);
		
		@Override
		protected UsernamePwAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException
		{
			if (!EqualsBuilder.reflectionEquals(USERNAME_PW_TOKEN, (UsernamePasswordToken) token))
			{
				throw new AuthenticationException();
			}
	
			return null;
		}
	}
}
