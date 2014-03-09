package org.openur.module.domain.security;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;

public class OpenURApplicationBuilder
	extends IdentifiableEntityBuilder<OpenURApplicationBuilder>
{
	private String applicationName = null;
	private String userName = null;
  private String password = null;
	
	public OpenURApplicationBuilder(String applicationName, String userName, String password)
	{
		super();
		
		init(applicationName, userName, password);
	}
	
	public OpenURApplicationBuilder(String identifier, String applicationName, String userName, String password)
	{
		super(identifier);
		
		init(applicationName, userName, password);
	}
	
	private void init(String applicationName, String userName, String password)
	{
		Validate.notEmpty(applicationName, "application-name must not be empty!");
		Validate.notEmpty(userName, "username must not be empty!");
		Validate.notEmpty(password, "password must not be empty!");
		this.applicationName = applicationName;
		this.userName = userName;
		this.password = password;
	}
	
	String getApplicationName()
	{
		return applicationName;
	}

	String getUserName()
	{
		return userName;
	}

	String getPassword()
	{
		return password;
	}
}
