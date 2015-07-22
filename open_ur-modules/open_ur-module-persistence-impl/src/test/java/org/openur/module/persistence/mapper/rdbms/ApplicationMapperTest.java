package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.persistence.rdbms.entity.PApplication;

public class ApplicationMapperTest
{
	private final String APP_NAME = "appName";
	
	@Test
	public void testMapFromDomainObject()
	{
		OpenURApplication immutable = new OpenURApplicationBuilder(APP_NAME).build();
		PApplication persistable = new ApplicationMapper().mapFromDomainObject(immutable);
		
		assertNotNull(persistable);
		assertTrue(ApplicationMapper.domainObjectEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PApplication persistable = new PApplication(APP_NAME);
		OpenURApplication immutable = new ApplicationMapper().mapFromEntity(persistable);
		
		assertNotNull(immutable);
		assertTrue(ApplicationMapper.domainObjectEqualsToEntity(immutable, persistable));
	}
}
