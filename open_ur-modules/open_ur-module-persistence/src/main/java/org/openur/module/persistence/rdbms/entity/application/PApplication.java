package org.openur.module.persistence.rdbms.entity.application;

import javax.persistence.Column;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;

public class PApplication
	extends AbstractOpenUrPersistable
{
	@Column(name="APPLICATION_NAME", nullable=false, unique=true)
	private String applicationName;

	public String getApplicationName()
	{
		return applicationName;
	}

	public void setApplicationName(String applicationName)
	{
		this.applicationName = applicationName;
	}

	PApplication()
	{
		// TODO Auto-generated constructor stub
	}

	public PApplication mapFromImmutable(OpenURApplication immutable)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
