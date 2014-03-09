package org.openur.module.domain.userstructure;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.openur.module.domain.userstructure.EMailAddress;

public class EMailAddressTest
{
	@Test
	public void testConvertToInternetAddress()
	{
		EMailAddress address = new EMailAddress("uwe@uwefuchs.com");
		assertEquals(
			"Uwe Fuchs <uwe@uwefuchs.com>", address.convertToInternetAddress("Uwe Fuchs").toString());
	}
}
