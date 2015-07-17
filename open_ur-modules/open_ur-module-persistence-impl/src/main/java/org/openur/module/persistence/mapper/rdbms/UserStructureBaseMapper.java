package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;
import org.openur.module.persistence.rdbms.entity.PUserStructureBase;

public class UserStructureBaseMapper
	extends AbstractEntityMapper
{
	protected static <I extends UserStructureBase, P extends PUserStructureBase>
			boolean immutableEqualsToEntity(I immutable, P persistable)
	{
		if (!AbstractEntityMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}

		return (immutable.getStatus() == persistable.getStatus());
	}

	protected static <UB extends UserStructureBaseBuilder<UB>, P extends PUserStructureBase>
			UB buildImmutable(UB immutableBuilder, P persistable)
	{
		immutableBuilder.status(persistable.getStatus());

		return AbstractEntityMapper.mapFromEntity(immutableBuilder, persistable);
	}
}
