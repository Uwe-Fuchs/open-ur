package org.openur.module.domain.application;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;

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
}
