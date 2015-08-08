package org.openur.remoting.webservice.application;

import org.glassfish.jersey.server.ResourceConfig;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;
import org.openur.remoting.xchange.rest.providers.json.TechnicalUserProvider;
import org.openur.remoting.xchange.rest.providers.json.UserSetProvider;

/**
 * Registers the components to be used by the JAX-RS application
 * 
 * @author info@uwefuchs.com
 */
public class OpenUrRestApplication
	extends ResourceConfig
{
	/**
	 * Register JAX-RS application components.
	 */
	public OpenUrRestApplication()
	{
		// declare packages to scan for REST-resources:
		packages("org.openur.remoting.resource");

		// register providers:
		register(PersonProvider.class);
		register(TechnicalUserProvider.class);
		register(UserSetProvider.class);
	}
}
