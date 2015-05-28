package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testUserStructureResource")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserStructureResourceTestSpringConfig.class})
public class UserResourceTest
	extends AbstractResourceTest
{	
	@Inject
	private IUserServices userServicesMock;
	
  @Before
	public void setUp()
		throws Exception
	{
    super.setUp();
	}

	@After
	public void tearDown()
		throws Exception
	{
		super.tearDown();
	}

//	@Test
//	public void testGetPersonById()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetPersonByNumber()
//	{
//		fail("Not yet implemented");
//	}	

  /**
   * Test to see that the message "Got it!" is sent in the response.
   */
  @Test
  public void testGetIt() {
      String responseMsg = target.path("userstructure").request().get(String.class);
      assertEquals("Got it!", responseMsg);
  }
}
