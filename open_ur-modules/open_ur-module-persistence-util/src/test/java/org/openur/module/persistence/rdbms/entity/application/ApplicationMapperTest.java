package org.openur.module.persistence.rdbms.entity.application;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.persistence.rdbms.entity.AbstractEntityMapperTest;

public class ApplicationMapperTest
{
	private final String APP_NAME = "appName";
	
	@Test
	public void testMapFromImmutable()
	{
		OpenURApplication immutable = new OpenURApplicationBuilder(APP_NAME).build();
		PApplication persistable = ApplicationMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(ApplicationMapperTest.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapToImmutable()
	{
		PApplication persistable = new PApplication();
		persistable.setApplicationName(APP_NAME);
		OpenURApplication immutable = ApplicationMapper.mapFromEntity(persistable);
		
		assertNotNull(immutable);
		assertTrue(ApplicationMapperTest.immutableEqualsToEntity(immutable, persistable));
	}

	public static boolean immutableEqualsToEntity(OpenURApplication immutable, PApplication persistable)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityIdentifiable(immutable, persistable))
		{
			return false;
		}
	
		return new EqualsBuilder()
				.append(immutable.getApplicationName(), persistable.getApplicationName())
				.isEquals();
	}
}
