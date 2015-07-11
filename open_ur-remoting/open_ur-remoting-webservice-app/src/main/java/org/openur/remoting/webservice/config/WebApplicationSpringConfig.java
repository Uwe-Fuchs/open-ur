package org.openur.remoting.webservice.config;

import org.openur.module.persistence.dao.IApplicationDao;
import org.openur.module.persistence.dao.IOrgUnitDao;
import org.openur.module.persistence.dao.IPersonDao;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.dao.ITechnicalUserDao;
import org.openur.module.persistence.dao.rdbms.ApplicationDaoImpl;
import org.openur.module.persistence.dao.rdbms.OrgUnitDaoImplRdbms;
import org.openur.module.persistence.dao.rdbms.PersonDaoImplRdbms;
import org.openur.module.persistence.dao.rdbms.SecurityDaoImplRdbms;
import org.openur.module.persistence.dao.rdbms.TechnicalUserDaoImplRdbms;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.module.service.userstructure.OrgUnitServicesImpl;
import org.openur.module.service.userstructure.UserServicesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebApplicationSpringConfig
{
	// services:
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

	// dao's:
	@Bean(name = "applicationDao")
	public IApplicationDao applicationDao()
	{		
		return new ApplicationDaoImpl();
	}

	@Bean(name = "personDao")
	public IPersonDao personDao()
	{		
		return new PersonDaoImplRdbms();
	}

	@Bean(name = "technicalUserDao")
	public ITechnicalUserDao technicalUserDao()
	{		
		return new TechnicalUserDaoImplRdbms();
	}

	@Bean(name = "orgUnitDao")
	public IOrgUnitDao orgUnitDao()
	{		
		return new OrgUnitDaoImplRdbms();
	}

	@Bean(name = "securityDao")
	public ISecurityDao securityDao()
	{		
		return new SecurityDaoImplRdbms();
	}
}
