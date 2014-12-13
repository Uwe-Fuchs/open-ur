package org.openur.module.persistence.rdbms.config;

import javax.inject.Inject;

import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.persistence.dao.rdbms.UserStructureDaoImplRdbms;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value="testUserStructureDao")
public class UserStructureDaoSpringConfig
{
	@Inject
	private PersonRepository personRepository;
	
	public UserStructureDaoSpringConfig()
	{
		super();
	}

	@Bean(name = "userStructureDao")
	public IUserStructureDao userStructureDao()
	{		
		UserStructureDaoImplRdbms _useStructureDao = new UserStructureDaoImplRdbms();
		_useStructureDao.setPersonRepository(personRepository);
		
		return _useStructureDao;
	}
}
