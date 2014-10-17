package org.openur.module.domain.application;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;

public class OpenURApplicationBuilder
	extends IdentifiableEntityBuilder<OpenURApplicationBuilder>
{
	private String applicationName = null;
	
	public OpenURApplicationBuilder(String applicationName)
	{
		super();
		
		init(applicationName);
	}
	
	public OpenURApplicationBuilder(String identifier, String applicationName)
	{
		super(identifier);
		
		init(applicationName);
	}
	
	private void init(String applicationName)
	{
		Validate.notEmpty(applicationName, "application-name must not be empty!");
		this.applicationName = applicationName;
	}
	
	public OpenURApplication build()
	{
		return new OpenURApplication(this);
	}
	
	String getApplicationName()
	{
		return applicationName;
	}
}
