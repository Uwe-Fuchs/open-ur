package org.openur.module.service.security;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class SecurityAuthTest
{
	@Before
	public void setUp()
		throws Exception
	{
	}

	@Test
	public void testAuthenticate()
	{
		// Nothing to test yet => job is done completely by Shiro.
		// maybe test the integration of different security-clients like Shiro, Spring-Sec, JAAS ??
		Assert.assertTrue(true);
	}

//	@Test
//	public void testHasPermissionInOrgUnit()
//	{
//		fail("Not yet implemented");
//	}

//  @Test
//  public void testHasPermission()
//  {
//	  fail("Not yet implemented");
//  }
}
