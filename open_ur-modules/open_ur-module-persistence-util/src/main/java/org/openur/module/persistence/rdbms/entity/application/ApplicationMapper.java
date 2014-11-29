package org.openur.module.persistence.rdbms.entity.application;

import org.apache.commons.lang3.StringUtils;
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

	public static OpenURApplication mapFromEntity(PApplication persistable)
	{
		OpenURApplicationBuilder immutableBuilder = new OpenURApplicationBuilder(persistable.getApplicationName());

		if (StringUtils.isNotEmpty(persistable.getIdentifier()))
		{
			immutableBuilder.identifier(persistable.getIdentifier());
		}

		return immutableBuilder
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
	}
}
