package org.openur.module.integration.security.shiro;

import static org.mockito.Mockito.mock;

import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.persistence.dao.ISecurityDomainDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value="testRealm")
public class RealmSpringConfig
{
	@Bean(name = "openUrRdbmsRealm")
	public OpenUrRdbmsRealm openUrRdbmsRealm()
	{
		return new OpenUrRdbmsRealm();
	}

	@Bean(name = "securityDaoMock")
	public ISecurityDomainDao securityDao()
	{		
		return mock(ISecurityDomainDao.class);
	}
}
