package org.openur.module.persistence.rdbms.config;

import javax.inject.Inject;

import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.persistence.dao.rdbms.UserStructureDaoImplRdbms;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value="testDao")
public class DaoSpringConfig
{
	@Inject
	private PersonRepository personRepository;
	
	@Inject
	private TechnicalUserRepository technicalUserRepository;
	
	public DaoSpringConfig()
	{
		super();
	}

	@Bean(name = "userStructureDao")
	public IUserStructureDao userStructureDao()
	{		
		UserStructureDaoImplRdbms _useStructureDao = new UserStructureDaoImplRdbms();
		_useStructureDao.setPersonRepository(personRepository);
		_useStructureDao.setTechnicalUserRepository(technicalUserRepository);
		
		return _useStructureDao;
	}
}
