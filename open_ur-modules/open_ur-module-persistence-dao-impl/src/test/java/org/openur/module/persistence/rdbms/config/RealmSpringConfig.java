package org.openur.module.persistence.rdbms.config;

import org.apache.shiro.realm.Realm;
import org.mockito.Mockito;
import org.openur.module.persistence.rdbms.repository.UserAccountRepository;
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

	@Bean(name = "userAccountRepositoryMock")
	public UserAccountRepository userAccountRepository()
	{
		return Mockito.mock(UserAccountRepository.class);
	}
}
