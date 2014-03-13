package org.openur.module.service.userstructure.user;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openur.module.persistence.userstructure.IUserStructureDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@ComponentScan(basePackages = {"org.openur.module.persistence", "org.openur.module.service"})
@Profile(value = "test")
public class TestSpringConfig
{
	public TestSpringConfig()
	{
		super();
		
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	private IUserStructureDao dao;

	@Bean(name = "userServices")
	public IUserServices userServices()
	{
		UserServicesImpl _userServices = new UserServicesImpl();
		_userServices.setUserStructureDao(userStructureDao());
		return _userServices;
	}

	@Bean(name = "userStructureDao")
	public IUserStructureDao userStructureDao()
	{		
		return this.dao;
	}
}
