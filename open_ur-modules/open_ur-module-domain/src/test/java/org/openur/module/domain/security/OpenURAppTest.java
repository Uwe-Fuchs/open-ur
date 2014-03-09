package org.openur.module.domain.security;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.UUID;

import org.junit.Test;

public class OpenURAppTest
{
	@Test
	public void testCompareTo()
	{
		String uuid = UUID.randomUUID().toString();
		OpenURApplication app1 = new OpenURApplication(new OpenURApplicationBuilder(uuid, "test-app-1", "pw", "secret"));
		String otherUuid = UUID.randomUUID().toString();
		OpenURApplication app2 = new OpenURApplication(new OpenURApplicationBuilder(otherUuid, "test-app-2", "pw", "secret"));		
		assertTrue("app1 should be before app2", app1.compareTo(app2) < 0);
		
		app2 = new OpenURApplication(new OpenURApplicationBuilder(otherUuid, "new-test-app", "pw", "secret"));
		assertTrue("app1 should be after app2", app1.compareTo(app2) > 0);
		
		app2 = new OpenURApplication(new OpenURApplicationBuilder(uuid, "test-app-1", "pw", "secret"));
		assertTrue("app1 and app2 should be on equal position", app1.compareTo(app2) == 0);

		app2 = new OpenURApplication(new OpenURApplicationBuilder(uuid, "test-app-2", "pw", "secret"));
		assertFalse("app1 and app2 should be on equal position", app1.compareTo(app2) == 0);
	}
}
