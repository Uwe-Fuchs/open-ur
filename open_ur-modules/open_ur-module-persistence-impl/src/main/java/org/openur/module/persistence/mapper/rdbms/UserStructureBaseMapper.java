package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;
import org.openur.module.persistence.rdbms.entity.PUserStructureBase;

public class UserStructureBaseMapper
	extends AbstractEntityMapper
{
	protected static <I extends UserStructureBase, P extends PUserStructureBase>
			boolean domainObjectEqualsToEntity(I domainObject, P entity)
	{
		if (!AbstractEntityMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}

		return (domainObject.getStatus() == entity.getStatus());
	}

	protected <UB extends UserStructureBaseBuilder<UB>, P extends PUserStructureBase>
			UB buildImmutable(UB immutableBuilder, P persistable)
	{
		immutableBuilder.status(persistable.getStatus());

		return super.mapFromEntity(immutableBuilder, persistable);
	}
}
