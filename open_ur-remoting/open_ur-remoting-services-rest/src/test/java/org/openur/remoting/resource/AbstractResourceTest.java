package org.openur.remoting.resource;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;

public class AbstractResourceTest
	extends JerseyTest
{
	@Override
	protected TestContainerFactory getTestContainerFactory()
	{
		return new GrizzlyWebTestContainerFactory();
	}

	@Override
	protected DeploymentContext configureDeployment()
	{
		return ServletDeploymentContext.forPackages(this.getClass().getPackage().getName()).contextPath("context").build();
	}
}
