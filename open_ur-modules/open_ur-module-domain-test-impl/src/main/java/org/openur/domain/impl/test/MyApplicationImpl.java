package org.openur.domain.impl.test;

import java.time.LocalDateTime;

import org.openur.module.domain.application.IApplication;

public class MyApplicationImpl
	implements IApplication
{
	private String identifier;
	private String applicationName;	

	public MyApplicationImpl(String identifier, String applicationName)
	{
		super();
		this.identifier = identifier;
		this.applicationName = applicationName;
	}

	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}

	@Override
	public String getApplicationName()
	{
		return this.applicationName;
	}

	@Override
	public LocalDateTime getLastModifiedDate()
	{
		return null;
	}

	@Override
	public LocalDateTime getCreationDate()
	{
		return null;
	}
}
