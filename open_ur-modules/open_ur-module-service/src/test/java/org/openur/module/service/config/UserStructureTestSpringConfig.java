package org.openur.module.service.config;

import static org.mockito.Mockito.mock;

import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.module.service.userstructure.OrgUnitServicesImpl;
import org.openur.module.service.userstructure.UserServicesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "test")
public class UserStructureTestSpringConfig
{
	@Bean(name = "userServices")
	public IUserServices userServices()
	{
		return new UserServicesImpl();
	}

	@Bean(name = "orgUnitServices")
	public IOrgUnitServices orgUnitServices()
	{
		return new OrgUnitServicesImpl();
	}

	@Bean(name = "userStructureDao")
	public IUserStructureDao userStructureDao()
	{		
		return mock(IUserStructureDao.class);
	}
}
