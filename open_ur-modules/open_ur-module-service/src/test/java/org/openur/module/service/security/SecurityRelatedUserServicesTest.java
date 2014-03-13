package org.openur.module.service.security;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class SecurityRelatedUserServicesTest
{

	@Before
	public void setUp()
		throws Exception
	{
	}

//	@Test
//	public void testObtainAllRoles()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindRolePerId()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainPermissionsPerRole()
//	{
//		fail("Not yet implemented");
//	}
}
