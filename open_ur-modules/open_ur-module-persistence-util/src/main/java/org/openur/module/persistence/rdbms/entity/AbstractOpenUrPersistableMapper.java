package org.openur.module.persistence.rdbms.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.util.exception.OpenURRuntimeException;

public class AbstractOpenUrPersistableMapper
{
	public static <I extends IdentifiableEntityImpl, P extends AbstractOpenUrPersistable> 
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
		
		return new EqualsBuilder()
				.append(immutable.getCreationDate(), persistable.getCreationDate())
				.append(immutable.getLastModifiedDate(), persistable.getLastModifiedDate())
				.isEquals();
	}
}
