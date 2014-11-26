package org.openur.module.domain.application;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;

public class OpenURApplicationBuilder
	extends IdentifiableEntityBuilder<OpenURApplicationBuilder>
{
	private String applicationName = null;
	
	// constructor:
	public OpenURApplicationBuilder(String applicationName)
	{
		super();
		
		Validate.notEmpty(applicationName, "application-name must not be empty!");
		this.applicationName = applicationName;
	}
	
	// builder:
	public OpenURApplication build()
	{
		return new OpenURApplication(this);
	}
	
	// accessors:
	String getApplicationName()
	{
		return applicationName;
	}
}
