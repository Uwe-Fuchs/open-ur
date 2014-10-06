package org.openur.module.persistence.rdbms.entity.application;

import javax.persistence.Column;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;

public class PApplication
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 4347267728528169905L;
	
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

	// constructor
	// package-scope for unittest-purposes:
	PApplication()
	{
		super();
	}

	public static PApplication mapFromImmutable(OpenURApplication immutable)
	{
		PApplication persistable = new PApplication();		
		persistable.setApplicationName(immutable.getApplicationName());
		
		return persistable;
	}
	
	public static OpenURApplication mapToImmutable(PApplication persistable)
	{
		OpenURApplication immutable = new OpenURApplicationBuilder(persistable.getApplicationName())
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
		
		return immutable;
	}
}
