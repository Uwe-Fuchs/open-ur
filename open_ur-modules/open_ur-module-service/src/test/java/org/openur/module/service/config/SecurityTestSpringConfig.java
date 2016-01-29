package org.openur.module.service.config;

import static org.mockito.Mockito.mock;

import org.apache.shiro.realm.Realm;
import org.openur.module.domain.security.authentication.IUsernamePasswordTokenBuilder;
import org.openur.module.domain.security.authentication.UsernamePasswordTokenBuilder;
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
	@Bean(name = "usernamePasswordTokenBuilder")
	public IUsernamePasswordTokenBuilder usernamePasswordTokenBuilder()
	{
		return new UsernamePasswordTokenBuilder();
	}
	
	@Bean(name = "authenticationServices")
	public IAuthenticationServices authenticationServices()
	{
		return new AuthenticationServicesImpl();
	}

	@Bean(name = "realmMock")
	public Realm realm()
	{
		return mock(Realm.class);
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
}
