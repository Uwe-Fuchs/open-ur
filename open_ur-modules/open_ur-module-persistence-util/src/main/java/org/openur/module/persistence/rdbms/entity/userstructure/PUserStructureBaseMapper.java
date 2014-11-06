package org.openur.module.persistence.rdbms.entity.userstructure;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistableMapper;

public class PUserStructureBaseMapper
{
	public static <I extends UserStructureBase, P extends PUserStructureBase> 
		boolean immutableEqualsToPersistable(I immutable, P persistable)
	{
		if (!AbstractOpenUrPersistableMapper.immutableEqualsToPersistable(immutable, persistable))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getNumber(), persistable.getNumber())
				.append(immutable.getStatus(), persistable.getStatus())
				.isEquals();
	}
}
