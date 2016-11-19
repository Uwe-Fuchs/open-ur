package org.openur.remoting.webservice.application;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.Validate;
import org.glassfish.jersey.server.ResourceConfig;
import org.openur.remoting.resource.errorhandling.AuthenticationExceptionMapper;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.resource.errorhandling.GenericExceptionMapper;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;
import org.openur.remoting.resource.secure_api.AuthenticationFilter_BasicAuth;
import org.openur.remoting.resource.secure_api.AuthenticationFilter_J2eePreAuth;
import org.openur.remoting.resource.secure_api.AuthenticationResultCheckFilter;
import org.openur.remoting.resource.secure_api.AuthorizationFilter;
import org.openur.remoting.resource.secure_api.ISecurityFilterChainBuilder;
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
	implements ISecurityFilterChainBuilder<ResourceConfig>
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
		String filterSetting = getEnvironmentFromApplicationContext().getProperty(AbstractSecurityFilterBase.SECURE_API_SETTINGS, 
					SecureApiSettings.NO_SECURITY.name());
		buildSecureApiFilterChain(filterSetting);
	}
	
	/**
	 * try to obtain {@link Environment}-reference from application-context.
	 * 
	 * @return {@link Environment}
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
		SecureApiSettings secureApiSettings = SecureApiSettings.valueOf(settings);
		
		buildSecurityFilterChain(secureApiSettings, this);
	}

	@Override
	public void buildFilterChainPreAuth(ResourceConfig configurable)
	{
		register(AuthenticationFilter_J2eePreAuth.class);	
		register(AuthenticationResultCheckFilter.class);	
	}

	@Override
	public void buildFilterChainBasicAuth(ResourceConfig configurable)
	{
		register(AuthenticationFilter_BasicAuth.class);		
		register(AuthenticationResultCheckFilter.class);	
	}

	@Override
	public void buildFilterChainDigestAuth(ResourceConfig configurable)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void buildFilterChainPermCheck(ResourceConfig configurable)
	{
		register(AuthorizationFilter.class);		
	}

	@Override
	public void buildFilterChainPreBasicAuth(ResourceConfig configurable)
	{
		register(AuthenticationFilter_J2eePreAuth.class);	
		register(AuthenticationFilter_BasicAuth.class);	
		register(AuthenticationResultCheckFilter.class);	
	}

	@Override
	public void buildFilterChainPreDigestAuth(ResourceConfig configurable)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void buildFilterChainPreAuthPermCheck(ResourceConfig configurable)
	{
		register(AuthenticationFilter_J2eePreAuth.class);	
		register(AuthenticationResultCheckFilter.class);	
		register(AuthorizationFilter.class);		
	}

	@Override
	public void buildFilterChainBasicAuthPermCheck(ResourceConfig configurable)
	{
		register(AuthenticationFilter_BasicAuth.class);	
		register(AuthenticationResultCheckFilter.class);	
		register(AuthorizationFilter.class);
	}

	@Override
	public void buildFilterChainDigestAuthPermCheck(ResourceConfig configurable)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void buildFilterChainPreBasicAuthPermCheck(ResourceConfig configurable)
	{
		register(AuthenticationFilter_J2eePreAuth.class);	
		register(AuthenticationFilter_BasicAuth.class);	
		register(AuthenticationResultCheckFilter.class);	
		register(AuthorizationFilter.class);
	}

	@Override
	public void buildFilterChainPreDigestAuthPermCheck(ResourceConfig configurable)
	{
		// TODO Auto-generated method stub		
	}
}
