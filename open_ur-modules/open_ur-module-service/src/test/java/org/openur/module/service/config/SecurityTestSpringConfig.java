package org.openur.module.service.config;

import static org.mockito.Mockito.mock;

import org.openur.module.persistence.dao.ISecurityDomainDao;
import org.openur.module.service.security.AuthorizationServicesImpl;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.securitydomain.ISecurityDomainServices;
import org.openur.module.service.securitydomain.SecurityDomainServicesImpl;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value="testSecurityServices")
public class SecurityTestSpringConfig
{	
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
	public ISecurityDomainDao securityDao()
	{		
		return mock(ISecurityDomainDao.class);
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
