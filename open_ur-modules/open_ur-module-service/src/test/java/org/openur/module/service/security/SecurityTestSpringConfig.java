package org.openur.module.service.security;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openur.module.persistence.security.ISecurityDao;
import org.openur.module.service.userstructure.orgunit.IOrgUnitServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "test")
public class SecurityTestSpringConfig
{
	public SecurityTestSpringConfig()
	{
		super();
		
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	private ISecurityDao securityDao;
	
	@Mock
	private IOrgUnitServices orgUnitServices;

	@Bean(name = "securityDomainServices")
	public ISecurityDomainServices securityDomainServices()
	{
		SecurityDomainServicesImpl _securityDomainServices = new SecurityDomainServicesImpl();
		_securityDomainServices.setSecurityDao(securityDao());
		return _securityDomainServices;
	}

	@Bean(name = "securityAuthServices")
	public ISecurityAuthServices securityAuthServices()
	{
		SecurityAuthServicesImpl _securityAuthServices = new SecurityAuthServicesImpl();
		_securityAuthServices.setOrgUnitServices(orgUnitServices());
		return _securityAuthServices;
	}

	@Bean(name = "securityDao")
	public ISecurityDao securityDao()
	{		
		return this.securityDao;
	}

	@Bean(name = "orgUnitServices")
	public IOrgUnitServices orgUnitServices()
	{		
		return this.orgUnitServices;
	}
}
