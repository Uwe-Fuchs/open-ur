package org.openur.remoting.client.ws.rs.secure_api;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;

import org.apache.commons.lang3.Validate;
import org.openur.remoting.resource.client.AbstractResourceClientBase;
import org.openur.remoting.resource.secure_api.SecureApiSettings;


public abstract class AbstractResourceClient 
	extends AbstractResourceClientBase
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

	@Override
	protected void setSecurityFilters(ClientBuilder builder)
	{
		SecureApiSettings settings = SecureApiSettings.valueOf(this.secureApiSettings);
		
		switch (settings)
		{
			case BASIC_AUTH:
				builder.register(new SecurityClientFilter_BasicAuth(userName, passWord));
				break;

			case BASIC_AUTH_PERMCHECK:
				builder.register(new SecurityClientFilter_BasicAuth(userName, passWord, applicationName));
				break;
				
			case DIGEST_AUTH:
				// TODO!
				break;
				
			case DIGEST_AUTH_PERMCHECK:
				// TODO!
				break;
				
			default:
				break;
		}
	}

	public AbstractResourceClient(String baseUrl, Class<?>... newProviders)
	{
		super(baseUrl, newProviders);
	}

	public AbstractResourceClient(String baseUrl)
	{
		super(baseUrl);
	}	
}
