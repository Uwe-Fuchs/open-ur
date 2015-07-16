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
	public void testMapFromImmutable()
	{
		OpenURApplication immutable = new OpenURApplicationBuilder(APP_NAME).build();
		PApplication persistable = ApplicationMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(ApplicationMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapToImmutable()
	{
		PApplication persistable = new PApplication(APP_NAME);
		OpenURApplication immutable = ApplicationMapper.mapFromEntity(persistable);
		
		assertNotNull(immutable);
		assertTrue(ApplicationMapper.immutableEqualsToEntity(immutable, persistable));
	}
}
