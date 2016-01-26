package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;
import org.openur.module.util.exception.OpenURRuntimeException;
import org.openur.module.util.processing.ObjectsComparator;

public class AbstractEntityMapper
{
	protected static <I extends IdentifiableEntityImpl, P extends AbstractOpenUrPersistable>
			boolean domainObjectEqualsToEntity(I domainObject, P entity)
	{
		if (domainObject == null && entity == null)
		{
			throw new OpenURRuntimeException("Both objects, immutable and entity, are null!");
		}

		if (domainObject == null || entity == null)
		{
			return false;
		}

		if (!ObjectsComparator.compareLocalDateTimes(domainObject.getCreationDate(), entity.getCreationDate()))
		{
			return false;
		}

		return ObjectsComparator.compareLocalDateTimes(domainObject.getLastModifiedDate(), entity.getLastModifiedDate());
	}

	protected <IB extends IdentifiableEntityBuilder<IB>, P extends AbstractOpenUrPersistable>
			IB mapFromEntity(IB immutableBuilder, P entity)
	{
		immutableBuilder
				.creationDate(entity.getCreationDate())
				.lastModifiedDate(entity.getLastModifiedDate());
		
		if (entity.getIdentifier() != null)
		{
			immutableBuilder.identifier(entity.getIdentifier());
		}

		return immutableBuilder;
	}
}
