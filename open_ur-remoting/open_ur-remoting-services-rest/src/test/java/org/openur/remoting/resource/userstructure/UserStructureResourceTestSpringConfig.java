package org.openur.remoting.resource.userstructure;

import static org.mockito.Mockito.mock;

import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "testUserStructureResource")
public class UserStructureResourceTestSpringConfig
{
	@Bean(name = "userServicesMock")
	public IUserServices userServices()
	{
		return mock(IUserServices.class);
	}

	@Bean(name = "orgUnitServicesMock")
	public IOrgUnitServices orgUnitServices()
	{
		return mock(IOrgUnitServices.class);
	}
}
