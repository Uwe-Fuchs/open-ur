package org.openur.module.service.security;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.module.service.security.SecurityDomainServicesImpl;
import org.openur.module.service.security.authorization.IAuthorizationServices;
import org.openur.module.service.security.authorization.AuthorizationServicesImpl;
import org.openur.module.service.userstructure.orgunit.IOrgUnitServices;
import org.openur.module.service.userstructure.user.IUserServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "test")
public class SecurityTestSpringConfig
{
	@Mock
	private ISecurityDao securityDao;
	
	@Mock
	private IOrgUnitServices orgUnitServices;
	
	@Mock
	private IUserServices userServices;
	
	public SecurityTestSpringConfig()
	{
		super();
		
		MockitoAnnotations.initMocks(this);
	}

	@Bean(name = "securityDomainServices")
	public ISecurityDomainServices securityDomainServices()
	{
		SecurityDomainServicesImpl _securityDomainServices = new SecurityDomainServicesImpl();
		//_securityDomainServices.setSecurityDao(securityDao());
		return _securityDomainServices;
	}

	@Bean(name = "authorizationServices")
	public IAuthorizationServices authorizationServices()
	{
		return new AuthorizationServicesImpl();
	}

	@Bean(name = "securityDao")
	public ISecurityDao securityDao()
	{		
		return this.securityDao;
	}

	@Bean(name = "userServices")
	public IUserServices userServices()
	{		
		return this.userServices;
	}

	@Bean(name = "orgUnitServices")
	public IOrgUnitServices orgUnitServices()
	{		
		return this.orgUnitServices;
	}
}
