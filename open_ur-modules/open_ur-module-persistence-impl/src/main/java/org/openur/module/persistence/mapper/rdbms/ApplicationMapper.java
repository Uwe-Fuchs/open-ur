package org.openur.module.persistence.mapper.rdbms;

import org.apache.commons.lang3.StringUtils;
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
