package org.openur.remoting.webservice.application;

import org.glassfish.jersey.server.ResourceConfig;
import org.openur.remoting.resource.errorhandling.AuthenticationExceptionMapper;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.resource.errorhandling.GenericExceptionMapper;
import org.openur.remoting.resource.secure_api.AuthenticationFilter;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;
import org.openur.remoting.xchange.rest.providers.json.PermissionProvider;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;
import org.openur.remoting.xchange.rest.providers.json.RoleProvider;
import org.openur.remoting.xchange.rest.providers.json.TechnicalUserProvider;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwAuthenticationInfoProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

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
		register(OrgUnitProvider.class);
		register(PermissionProvider.class);
		register(RoleProvider.class);
		register(IdentifiableEntitySetProvider.class);
		register(UsernamePwTokenProvider.class);
		register(UsernamePwAuthenticationInfoProvider.class);
		register(AuthenticationExceptionMapper.class);
		register(EntityNotFoundExceptionMapper.class);
		register(GenericExceptionMapper.class);
		register(ErrorMessageProvider.class);
		register(AuthenticationFilter.class);
	}
}
