package org.openur.module.domain.userstructure.user.technicaluser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;

public class TechnicalUserTest
{
	@Test
	public void testCompareTo()
	{
		TechnicalUserBuilder tub = new TechnicalUserBuilder("user1", "pw1");
		tub.number("abc");
		tub.status(Status.ACTIVE);
		TechnicalUser tu1 = new TechnicalUser(tub);
		
		tub = new TechnicalUserBuilder("user2", "pw2");
		tub.number("xyz");
		TechnicalUser tu2 = new TechnicalUser(tub);
		
		assertTrue(tu1.compareTo(tu2) < 0);
	}
}
