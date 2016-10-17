package org.openur.remoting.client.ws.rs.secure_api;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;

import org.apache.commons.lang3.Validate;
import org.openur.remoting.resource.client.AbstractResourceClientBase;
import org.openur.remoting.resource.secure_api.ISecurityFilterChainBuilder;
import org.openur.remoting.resource.secure_api.SecureApiSettings;


public abstract class AbstractResourceClient 
	extends AbstractResourceClientBase
	implements ISecurityFilterChainBuilder<ClientBuilder>
{	
	private String secureApiSettings = SecureApiSettings.NO_SECURITY.name();
	private String applicationName;
	private String userName;
	private String passWord;

	@Inject
	public void setSecureApiSettings(String secureApiSettings)
	{
		Validate.notBlank(secureApiSettings, "API-settings must not be blank!");
		this.secureApiSettings = secureApiSettings;
	}
	
	@Inject
	public void setApplicationName(String applicationName)
	{
		Validate.notBlank(applicationName, "application-name must not be blank!");
		this.applicationName = applicationName;
	}

	@Inject
	public void setUserName(String userName)
	{
		Validate.notBlank(userName, "user-name must not be blank!");
		this.userName = userName;
	}

	@Inject
	public void setPassWord(String passWord)
	{
		Validate.notBlank(passWord, "password must not be blank!");
		this.passWord = passWord;
	}

	public AbstractResourceClient(String baseUrl, Class<?>... newProviders)
	{
		super(baseUrl, newProviders);
	}

	public AbstractResourceClient(String baseUrl)
	{
		super(baseUrl);
	}	

	@Override
	protected void setSecurityFilters(ClientBuilder builder)
	{
		SecureApiSettings settings = SecureApiSettings.valueOf(this.secureApiSettings);
		
		buildSecurityFilterChain(settings, builder);
	}

	@Override
	public void buildFilterChainPreAuth(ClientBuilder configurable)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void buildFilterChainBasicAuth(ClientBuilder builder)
	{
		builder.register(new SecurityClientFilter_BasicAuth(userName, passWord));		
	}

	@Override
	public void buildFilterChainDigestAuth(ClientBuilder builder)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void buildFilterChainPermCheck(ClientBuilder configurable)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void buildFilterChainPreBasicAuth(ClientBuilder builder)
	{
		buildFilterChainBasicAuth(builder);
	}

	@Override
	public void buildFilterChainPreDigestAuth(ClientBuilder builder)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void buildFilterChainPreAuthPermCheck(ClientBuilder builder)
	{
		// do nothing in REST-Client
	}

	@Override
	public void buildFilterChainBasicAuthPermCheck(ClientBuilder builder)
	{
		builder.register(new SecurityClientFilter_BasicAuth(userName, passWord, applicationName));	
	}

	@Override
	public void buildFilterChainDigestAuthPermCheck(ClientBuilder builder)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void buildFilterChainPreBasicAuthPermCheck(ClientBuilder builder)
	{
		buildFilterChainBasicAuthPermCheck(builder);
	}

	@Override
	public void buildFilterChainPreDigestAuthPermCheck(ClientBuilder builder)
	{
		// TODO Auto-generated method stub		
	}
}
