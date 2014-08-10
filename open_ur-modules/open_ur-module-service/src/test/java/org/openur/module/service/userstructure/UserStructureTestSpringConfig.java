package org.openur.module.service.userstructure;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.service.userstructure.orgunit.IOrgUnitServices;
import org.openur.module.service.userstructure.orgunit.OrgUnitServicesImpl;
import org.openur.module.service.userstructure.user.IUserServices;
import org.openur.module.service.userstructure.user.UserServicesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@ComponentScan(basePackages = {"org.openur.module.persistence", "org.openur.module.service"})
@Profile(value = "test")
public class UserStructureTestSpringConfig
{
	public UserStructureTestSpringConfig()
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

	@Bean(name = "orgUnitServices")
	public IOrgUnitServices orgUnitServices()
	{
		OrgUnitServicesImpl _orgUnitServices = new OrgUnitServicesImpl();
		_orgUnitServices.setUserStructureDao(userStructureDao());
		return _orgUnitServices;
	}

	@Bean(name = "userStructureDao")
	public IUserStructureDao userStructureDao()
	{		
		return this.dao;
	}
}
