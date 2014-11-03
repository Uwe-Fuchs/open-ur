package org.openur.module.persistence.rdbms.entity.application;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;

public class ApplicationMapperTest
{
	private final String APP_NAME = "app1";
	
	@Test
	public void testMapFromImmutable()
	{
		OpenURApplication immutable = new OpenURApplicationBuilder(APP_NAME).build();
		PApplication persistable = ApplicationMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(ApplicationMapper.immutableEqualsToPersistable(immutable, persistable));
	}

	@Test
	public void testMapToImmutable()
	{
		PApplication persistable = new PApplication();
		persistable.setApplicationName(APP_NAME);
		OpenURApplication immutable = ApplicationMapper.mapToImmutable(persistable);
		
		assertNotNull(immutable);
		assertTrue(ApplicationMapper.immutableEqualsToPersistable(immutable, persistable));
	}
}
