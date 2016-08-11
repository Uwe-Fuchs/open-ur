package org.openur.remoting.webservice.application;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.Validate;
import org.glassfish.jersey.server.ResourceConfig;
import org.openur.remoting.resource.errorhandling.AuthenticationExceptionMapper;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.resource.errorhandling.GenericExceptionMapper;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilter;
import org.openur.remoting.resource.secure_api.AuthenticationFilter_BasicAuth;
import org.openur.remoting.resource.secure_api.AuthorizationFilter;
import org.openur.remoting.resource.secure_api.SecureApiSettings;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;
import org.openur.remoting.xchange.rest.providers.json.PermissionProvider;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;
import org.openur.remoting.xchange.rest.providers.json.RoleProvider;
import org.openur.remoting.xchange.rest.providers.json.TechnicalUserProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwAuthenticationInfoProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Registers the components to be used by the JAX-RS application
 * 
 * @author info@uwefuchs.com
 */
public class OpenUrRestApplication
	extends ResourceConfig
{
	private Environment env;
	private ServletContext servletContext;
	
	/**
	 * Register JAX-RS application components.
	 */
	public OpenUrRestApplication(@Context ServletContext servletContext)
	{
		this.servletContext = servletContext;
		
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
		
		// build filter-chain for securing API:
		String filterSetting = getEnvironmentFromApplicationContext().getProperty(AbstractSecurityFilter.SECURE_API_SETTINGS, 
					SecureApiSettings.NO_SECURITY.name());
		buildSecureApiFilterChain(filterSetting);
	}
	
	/**
	 * try to obtain {@link Environment}-reference from application-context:
	 * @return
	 */
	private Environment getEnvironmentFromApplicationContext()
	{
		if (env == null)
		{
			try
			{
				ApplicationContext context = (ApplicationContext) servletContext.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
				env = context.getEnvironment();
				Validate.notNull(env, "Environment should not be null! Please check your Spring-ApplicationContext-settings!");
			} catch (Exception e)
			{
				throw new RuntimeException("failed to obtain Environment from Spring-ApplicationContext!", e);
			}
		}

		return env;		
	}
	
	private void buildSecureApiFilterChain(String settings)
	{
		if (SecureApiSettings.NO_SECURITY.name().equals(settings))
		{
			return;
		}
		
		String permCheck = SecureApiSettings.BASIC_AUTH_PERMCHECK.name().substring(11);
		
		if (settings.contains(permCheck))
		{
			register(AuthorizationFilter.class);
		}
		
		if (settings.contains(SecureApiSettings.BASIC_AUTH.name()))
		{
			register(AuthenticationFilter_BasicAuth.class);
			
			return;
		}
		
		if (settings.contains(SecureApiSettings.DIGEST_AUTH.name()))
		{
			// TODO!
			return;
		}
	}
}
