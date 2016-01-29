package org.openur.module.domain.application;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.util.processing.DefaultsUtil;

public class OpenURAppTest
{
	@Test
	public void testCompareTo()
	{
		final String uuid = UUID.randomUUID().toString();
		OpenURApplication app1 = new OpenURApplicationBuilder("test-app-1")
				.identifier(uuid)
				.build();
		
		final String otherUuid = UUID.randomUUID().toString();
		OpenURApplication app2 = new OpenURApplicationBuilder("test-app-2")
				.identifier(otherUuid)
				.build();		
		assertTrue("app1 should be before app2", app1.compareTo(app2) < 0);
		
		app2 = new OpenURApplicationBuilder("new-test-app")
				.identifier(otherUuid)
				.build();
		assertTrue("app1 should be after app2", app1.compareTo(app2) > 0);
		
		app2 = new OpenURApplicationBuilder("test-app-1")
				.identifier(uuid)
				.build();
		assertTrue("app1 and app2 should be on equal position", app1.compareTo(app2) == 0);

		app2 = new OpenURApplicationBuilder("test-app-2")
				.identifier(uuid)
				.build();
		assertFalse("app1 and app2 should be on equal position", app1.compareTo(app2) == 0);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCompareToNoApplicationName()
	{
		MyApplication app1 = new MyApplication();
		MyApplication app2 = new MyApplication();
		
		app1.compareTo(app2);
	}
	
	private class MyApplication
		implements IApplication
	{
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getIdentifier()
		{
			return DefaultsUtil.getRandomIdentifierByDefaultMechanism();
		}

		@Override
		public LocalDateTime getLastModifiedDate()
		{
			return null;
		}

		@Override
		public LocalDateTime getCreationDate()
		{
			return null;
		}

		@Override
		public String getApplicationName()
		{
			return null;
		}		
	}
}
