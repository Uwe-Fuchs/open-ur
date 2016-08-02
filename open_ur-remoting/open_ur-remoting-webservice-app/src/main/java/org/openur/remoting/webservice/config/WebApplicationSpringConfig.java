package org.openur.remoting.webservice.config;

import javax.inject.Inject;

import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
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
import org.openur.module.persistence.mapper.rdbms.AddressMapper;
import org.openur.module.persistence.mapper.rdbms.ApplicationMapper;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper.OrgUnitMemberMapper;
import org.openur.module.persistence.mapper.rdbms.PermissionMapper;
import org.openur.module.persistence.mapper.rdbms.PersonMapper;
import org.openur.module.persistence.mapper.rdbms.RoleMapper;
import org.openur.module.persistence.mapper.rdbms.TechnicalUserMapper;
import org.openur.module.persistence.mapper.rdbms.UserAccountMapper;
import org.openur.module.service.security.AuthorizationServicesImpl;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.module.service.security.SecurityDomainServicesImpl;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.module.service.userstructure.OrgUnitServicesImpl;
import org.openur.module.service.userstructure.UserServicesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class WebApplicationSpringConfig
{
	@Inject
	protected Environment env;
	
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

	@Bean(name = "securityDomianServices")
	public ISecurityDomainServices securityDomianServices()
	{
		return new SecurityDomainServicesImpl();
	}

	@Bean(name = "authorizationServices")
	public IAuthorizationServices authorizationServices()
	{
		return new AuthorizationServicesImpl();
	}
	
	// security-realm:
	@Bean(name = "openUrRdbmsRealm")
	public Realm openUrRdbmsRealm()
	{
		OpenUrRdbmsRealm realm = new OpenUrRdbmsRealm();
		realm.setCredentialsMatcher(new SimpleCredentialsMatcher());
		
		return realm;
	}

	@Bean(name = "hashCredentials")
	public Boolean hashCredentials()
	{
		String hashCredentialsStr = env.getProperty("hashCredentials", "FALSE");
		
		return Boolean.valueOf(hashCredentialsStr);
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
	
	// mappers:
	@Bean(name = "addressMapper")
	public AddressMapper addressMapper()
	{		
		return new AddressMapper();
	}

	@Bean(name = "applicationMapper")
	public ApplicationMapper applicationMapper()
	{		
		return new ApplicationMapper();
	}

	@Bean(name = "personMapper")
	public PersonMapper personMapper()
	{		
		return new PersonMapper();
	}

	@Bean(name = "technicalUserMapper")
	public TechnicalUserMapper technicalUserMapper()
	{		
		return new TechnicalUserMapper();
	}

	@Bean(name = "userAccountMapper")
	public UserAccountMapper userAccountMapper()
	{		
		return new UserAccountMapper();
	}

	@Bean(name = "orgUnitMapper")
	public OrganizationalUnitMapper orgUnitMapper()
	{		
		return new OrganizationalUnitMapper();
	}

	@Bean(name = "orgUnitMemberMapper")
	public OrgUnitMemberMapper orgUnitMemberMapper()
	{		
		return new OrgUnitMemberMapper();
	}

	@Bean(name = "permissionMapper")
	public PermissionMapper permissionMapper()
	{		
		return new PermissionMapper();
	}

	@Bean(name = "roleMapper")
	public RoleMapper roleMapper()
	{		
		return new RoleMapper();
	}
}
