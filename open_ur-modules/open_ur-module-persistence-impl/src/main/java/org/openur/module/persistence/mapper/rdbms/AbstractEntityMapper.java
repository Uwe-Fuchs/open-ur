package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;
import org.openur.module.util.ObjectsComparator;
import org.openur.module.util.exception.OpenURRuntimeException;

public class AbstractEntityMapper
{
	protected static <I extends IdentifiableEntityImpl, P extends AbstractOpenUrPersistable>
			boolean immutableEqualsToEntity(I immutable, P persistable)
	{
		if (immutable == null && persistable == null)
		{
			throw new OpenURRuntimeException("Both objects, immutable and entity, are null!");
		}

		if (immutable == null || persistable == null)
		{
			return false;
		}

		if (!ObjectsComparator.compareLocalDateTimes(immutable.getCreationDate(), persistable.getCreationDate()))
		{
			return false;
		}

		return ObjectsComparator.compareLocalDateTimes(immutable.getLastModifiedDate(), persistable.getLastModifiedDate());
	}

	protected <IB extends IdentifiableEntityBuilder<IB>, P extends AbstractOpenUrPersistable>
			IB mapFromEntity(IB immutableBuilder, P persistable)
	{
		immutableBuilder
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate());

		return immutableBuilder;
	}
}
