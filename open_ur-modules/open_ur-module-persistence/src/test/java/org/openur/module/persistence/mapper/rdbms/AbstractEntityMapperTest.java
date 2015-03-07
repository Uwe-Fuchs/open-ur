package org.openur.module.persistence.mapper.rdbms;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;
import org.openur.module.persistence.rdbms.entity.PUserStructureBase;
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

		if (StringUtils.isNotEmpty(immutable.getIdentifier()) && StringUtils.isNotEmpty(persistable.getIdentifier())
			&& !immutable.getIdentifier().equals(persistable.getIdentifier()))
		{
			return false;
		}

		return compareLocalDateTimes(immutable.getCreationDate(), persistable.getCreationDate());
	}

	public static <I extends UserStructureBase, P extends PUserStructureBase> 
		boolean immutableEqualsToEntityUserStructureBase(I immutable, P persistable)
	{
		if (!immutableEqualsToEntityIdentifiable(immutable, persistable))
		{
			return false;
		}

		return (immutable.getStatus() == persistable.getStatus());
	}

	private static boolean compareLocalDateTimes(LocalDateTime first, LocalDateTime second)
	{
		if (first == null && second == null)
		{
			return true;
		}

		if (first == null || second == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(first.getYear(), second.getYear())
				.append(first.getMonth(), second.getMonth())
				.append(first.getDayOfMonth(), second.getDayOfMonth())
				.append(first.getHour(), second.getHour())
				.append(first.getMinute(), second.getMinute())
				.append(first.getSecond(), second.getSecond())
				.isEquals();
	}
}
