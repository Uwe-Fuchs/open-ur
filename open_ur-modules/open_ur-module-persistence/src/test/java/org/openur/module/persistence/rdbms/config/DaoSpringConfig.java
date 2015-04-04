package org.openur.module.persistence.rdbms.config;

import org.openur.module.persistence.dao.IOrgUnitDao;
import org.openur.module.persistence.dao.IPersonDao;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.dao.ITechnicalUserDao;
import org.openur.module.persistence.dao.rdbms.OrgUnitDaoImplRdbms;
import org.openur.module.persistence.dao.rdbms.PersonDaoImplRdbms;
import org.openur.module.persistence.dao.rdbms.SecurityDaoImplRdbms;
import org.openur.module.persistence.dao.rdbms.TechnicalUserDaoImplRdbms;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value="testDao")
public class DaoSpringConfig
{
	public DaoSpringConfig()
	{
		super();
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
