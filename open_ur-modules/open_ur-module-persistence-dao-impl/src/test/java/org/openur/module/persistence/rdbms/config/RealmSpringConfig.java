package org.openur.module.persistence.rdbms.config;

import static org.mockito.Mockito.mock;

import org.apache.shiro.realm.Realm;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.realm.rdbms.OpenUrRdbmsRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value="testRealm")
public class RealmSpringConfig
{
	@Bean(name = "openUrRdbmsRealm")
	public Realm openUrRdbmsRealm()
	{
		return new OpenUrRdbmsRealm();
	}

	@Bean(name = "securityDaoMock")
	public ISecurityDao securityDao()
	{		
		return mock(ISecurityDao.class);
	}
}
