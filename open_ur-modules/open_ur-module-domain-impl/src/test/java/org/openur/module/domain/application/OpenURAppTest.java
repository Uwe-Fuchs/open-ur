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
		String uuid = UUID.randomUUID().toString();
		OpenURApplication app1 = new OpenURApplicationBuilder(uuid, "test-app-1").build();
		String otherUuid = UUID.randomUUID().toString();
		OpenURApplication app2 = new OpenURApplicationBuilder(otherUuid, "test-app-2").build();		
		assertTrue("app1 should be before app2", app1.compareTo(app2) < 0);
		
		app2 = new OpenURApplicationBuilder(otherUuid, "new-test-app").build();
		assertTrue("app1 should be after app2", app1.compareTo(app2) > 0);
		
		app2 = new OpenURApplicationBuilder(uuid, "test-app-1").build();
		assertTrue("app1 and app2 should be on equal position", app1.compareTo(app2) == 0);

		app2 = new OpenURApplicationBuilder(uuid, "test-app-2").build();
		assertFalse("app1 and app2 should be on equal position", app1.compareTo(app2) == 0);
	}
}
