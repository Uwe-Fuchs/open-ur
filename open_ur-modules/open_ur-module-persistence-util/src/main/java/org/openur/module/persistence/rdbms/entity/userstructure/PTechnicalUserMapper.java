package org.openur.module.persistence.rdbms.entity.userstructure;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUserBuilder;

public class PTechnicalUserMapper
{
	public static PTechnicalUser mapFromImmutable(TechnicalUser immutable)
	{
		PTechnicalUser persistable = new PTechnicalUser();
		
		persistable.setNumber(immutable.getNumber());
		persistable.setStatus(immutable.getStatus());
		
		return persistable;
	}
	
	public static TechnicalUser mapToImmutable(PTechnicalUser persistable)
	{
		TechnicalUserBuilder builder = new TechnicalUserBuilder()
				.number(persistable.getNumber())
				.status(persistable.getStatus())
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate());
		
		return builder.build();
	}
	
	public static boolean immutableEqualsToPersistable(TechnicalUser immutable, PTechnicalUser persistable)
	{
		if (immutable == null || persistable == null)
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getNumber(), persistable.getNumber())
				.append(immutable.getStatus(), persistable.getStatus())
				.append(immutable.getCreationDate(), persistable.getCreationDate())
				.append(immutable.getLastModifiedDate(), persistable.getLastModifiedDate())
				.isEquals();
	}
}
