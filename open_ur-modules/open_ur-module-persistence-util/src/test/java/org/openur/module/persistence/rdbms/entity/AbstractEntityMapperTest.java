package org.openur.module.persistence.rdbms.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.persistence.rdbms.entity.userstructure.PUserStructureBase;
import org.openur.module.util.exception.OpenURRuntimeException;

public class AbstractEntityMapperTest
{
	public static <I extends IdentifiableEntityImpl, P extends AbstractOpenUrPersistable> 
		boolean immutableEqualsToEntityIdentifiable(I immutable, P persistable)
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

	public static <I extends UserStructureBase, P extends PUserStructureBase> 
		boolean immutableEqualsToEntityUserStructureBase(I immutable, P persistable)
	{
		if (!immutableEqualsToEntityIdentifiable(immutable, persistable))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getStatus(), persistable.getStatus())
				.isEquals();
	}
}
