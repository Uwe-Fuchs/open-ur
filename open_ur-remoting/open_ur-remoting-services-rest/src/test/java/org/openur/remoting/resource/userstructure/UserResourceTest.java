package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.service.userstructure.IUserServices;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testUserStructureResource")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ UserStructureResourceTestSpringConfig.class })
public class UserResourceTest
	extends JerseyTest
{
	 @Inject
	 private IUserServices userServicesMock;

	@Override
	protected TestContainerFactory getTestContainerFactory()
	{
		return new GrizzlyWebTestContainerFactory();
	}

	@Override
	protected DeploymentContext configureDeployment()
	{
		return ServletDeploymentContext.forPackages(this.getClass().getPackage().getName())
      .contextPath("context").build();
	}

//	@Override
//	protected Application configure()
//	{
//		ResourceConfig rc = new ResourceConfig();
//		rc.property("testUserStructureResource", new AnnotationConfigApplicationContext(UserStructureResourceTestSpringConfig.class));
//		rc.register(SpringLifecycleListener.class).register(RequestContextFilter.class);
//		rc.registerClasses(UserResource.class);
//
//		return rc;
//	}

	// @Test
	// public void testGetPersonById()
	// {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetPersonByNumber()
	// {
	// fail("Not yet implemented");
	// }

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testGetIt()
	{
		String responseMsg = target().path("userstructure").request().get(String.class);
		assertEquals("Got it!", responseMsg);
	}
}
