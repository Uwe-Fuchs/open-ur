package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.persistence.rdbms.entity.PApplication;

/**
 * @author uwe
 * 
 * maps entity-data from and to immutable.
 */
public class ApplicationMapper
	extends AbstractEntityMapper
{
	public static PApplication mapFromImmutable(OpenURApplication immutable)
	{
		return new PApplication(immutable.getApplicationName());
	}

	public static OpenURApplication mapFromEntity(PApplication persistable)
	{
		OpenURApplicationBuilder immutableBuilder = new OpenURApplicationBuilder(persistable.getApplicationName());
		
		return AbstractEntityMapper.mapFromEntity(immutableBuilder, persistable)
					.build();
	}

	public static boolean immutableEqualsToEntity(OpenURApplication immutable, PApplication persistable)
	{
		if (!AbstractEntityMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		return immutable.getApplicationName().equals(persistable.getApplicationName());
	}
}
