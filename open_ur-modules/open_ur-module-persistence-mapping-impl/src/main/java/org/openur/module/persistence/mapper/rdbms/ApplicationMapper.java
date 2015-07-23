package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.persistence.rdbms.entity.PApplication;

public class ApplicationMapper
	extends AbstractEntityMapper implements IApplicationMapper<OpenURApplication>
{
	@Override
	public PApplication mapFromDomainObject(OpenURApplication domainObject)
	{
		return new PApplication(domainObject.getApplicationName());
	}

	@Override
	public OpenURApplication mapFromEntity(PApplication entity)
	{
		OpenURApplicationBuilder immutableBuilder = new OpenURApplicationBuilder(entity.getApplicationName());
		
		return super.mapFromEntity(immutableBuilder, entity)
					.build();
	}

	public static boolean domainObjectEqualsToEntity(OpenURApplication domainObject, PApplication entity)
	{
		if (!AbstractEntityMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		return domainObject.getApplicationName().equals(entity.getApplicationName());
	}
}
