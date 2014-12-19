package org.openur.module.persistence.rdbms.config;

import javax.inject.Inject;

import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.persistence.dao.rdbms.SecurityDaoImplRdbms;
import org.openur.module.persistence.dao.rdbms.UserStructureDaoImplRdbms;
import org.openur.module.persistence.rdbms.repository.PermissionRepository;
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
	
	@Inject
	private PermissionRepository permissionRepository;
	
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

	@Bean(name = "securityDao")
	public ISecurityDao securityDao()
	{		
		SecurityDaoImplRdbms _securityDao = new SecurityDaoImplRdbms();
		_securityDao.setPermissionRepository(permissionRepository);
		
		return _securityDao;
	}
}
