package org.openur.module.persistence.rdbms.entity.application;

import org.apache.commons.lang3.builder.EqualsBuilder;
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
	
	public static boolean immutableEqualsToPersistable(OpenURApplication immutable, PApplication persistable)
	{
		if (immutable == null || persistable == null)
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getApplicationName(), persistable.getApplicationName())
				.append(immutable.getCreationDate(), persistable.getCreationDate())
				.append(immutable.getLastModifiedDate(), persistable.getLastModifiedDate())
				.isEquals();
	}
}
