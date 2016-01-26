package org.openur.module.domain.userstructure.technicaluser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser.TechnicalUserBuilder;
import org.openur.module.util.data.Status;

public class TechnicalUserTest
{
	@Test
	public void testCompareTo()
	{
		TechnicalUserBuilder tub = new TechnicalUserBuilder("abc");
		tub.status(Status.ACTIVE);
		TechnicalUser tu1 = tub.build();
		
		tub = new TechnicalUserBuilder("xyz");
		TechnicalUser tu2 = tub.build();
		
		assertTrue(tu1.compareTo(tu2) < 0);
	}
}
