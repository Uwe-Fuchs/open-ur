package org.openur.module.persistence.rdbms.config;

import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.persistence.dao.rdbms.SecurityDaoImplRdbms;
import org.openur.module.persistence.dao.rdbms.UserStructureDaoImplRdbms;
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

	@Bean(name = "userStructureDao")
	public IUserStructureDao userStructureDao()
	{		
		return new UserStructureDaoImplRdbms();
	}

	@Bean(name = "securityDao")
	public ISecurityDao securityDao()
	{		
		return new SecurityDaoImplRdbms();
	}
}
