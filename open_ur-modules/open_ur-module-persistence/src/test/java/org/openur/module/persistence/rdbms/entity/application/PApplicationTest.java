package org.openur.module.persistence.rdbms.entity.application;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;

public class PApplicationTest
{
	private final String APP_NAME = "app1";
	
	@Test
	public void testMapFromImmutable()
	{
		OpenURApplication immutable = new OpenURApplicationBuilder(APP_NAME).build();
		PApplication persistable = PApplication.mapFromImmutable(immutable);
		
		assertEquals(persistable.getApplicationName(), APP_NAME);
	}

	@Test
	public void testMapToImmutable()
	{
		PApplication persistable = new PApplication();
		persistable.setApplicationName(APP_NAME);
		OpenURApplication immutable = PApplication.mapToImmutable(persistable);
		
		assertEquals(immutable.getApplicationName(), APP_NAME);
	}
}
