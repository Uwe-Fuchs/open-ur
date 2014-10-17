package org.openur.module.persistence.rdbms.entity.application;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;

/**
 * @author uwe
 * 
 * maps entity-data from and to immutable.
 */
public class ApplicationMapper
{
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
