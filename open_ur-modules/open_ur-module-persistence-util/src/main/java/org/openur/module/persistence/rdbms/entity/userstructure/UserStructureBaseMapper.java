package org.openur.module.persistence.rdbms.entity.userstructure;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistableMapper;

public class UserStructureBaseMapper
{
	public static <I extends UserStructureBase, P extends PUserStructureBase> 
		boolean immutableEqualsToEntity(I immutable, P persistable)
	{
		if (!AbstractOpenUrPersistableMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getStatus(), persistable.getStatus())
				.isEquals();
	}
}
