package org.openur.module.persistence.mapper.rdbms;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.persistence.rdbms.entity.PApplication;

/**
 * @author uwe
 * 
 * maps entity-data from and to immutable.
 */
public class ApplicationMapper
{
	public static PApplication mapFromImmutable(OpenURApplication immutable)
	{
		return new PApplication(immutable.getApplicationName());
	}

	public static OpenURApplication mapFromEntity(PApplication persistable)
	{
		return mapInternal(persistable).build();
	}
	
	public static OpenURApplication mapFromEntityWithIdentifier(PApplication persistable, String identifier)
	{
		return mapInternal(persistable)
				.identifier(identifier)
				.build();
	}
	
	private static OpenURApplicationBuilder mapInternal(PApplication persistable)
	{
		OpenURApplicationBuilder immutableBuilder = new OpenURApplicationBuilder(persistable.getApplicationName());
		
		return immutableBuilder
			.creationDate(persistable.getCreationDate())
			.lastModifiedDate(persistable.getLastModifiedDate());
	}

	public static boolean immutableEqualsToEntity(OpenURApplication immutable, PApplication persistable)
	{
		return new EqualsBuilder()
		.append(immutable.getApplicationName(), persistable.getApplicationName())
		.isEquals();
	}
}