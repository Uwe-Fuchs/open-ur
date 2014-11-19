package org.openur.module.persistence.rdbms.entity.application;

import org.apache.commons.lang3.StringUtils;
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
	
	public static OpenURApplication mapFromEntity(PApplication persistable)
	{
		OpenURApplicationBuilder immutableBuilder;
		
		if (StringUtils.isNotEmpty(persistable.getIdentifier()))
		{
			immutableBuilder = new OpenURApplicationBuilder(persistable.getIdentifier(), persistable.getApplicationName());			
		}
		else
		{
			immutableBuilder = new OpenURApplicationBuilder(persistable.getApplicationName());
		}			
		
		immutableBuilder
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
		
		return immutableBuilder.build();
	}
	
	public static boolean immutableEqualsToEntity(OpenURApplication immutable, PApplication persistable)
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
