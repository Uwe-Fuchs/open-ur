package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser.TechnicalUserBuilder;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;

public class TechnicalUserMapper
{
	public static PTechnicalUser mapFromImmutable(TechnicalUser immutable)
	{
		PTechnicalUser persistable = new PTechnicalUser(immutable.getTechUserNumber());

		persistable.setStatus(immutable.getStatus());
		
		return persistable;
	}
	
	public static TechnicalUser mapFromEntity(PTechnicalUser persistable)
	{
		TechnicalUserBuilder immutableBuilder = new TechnicalUserBuilder(persistable.getTechUserNumber());
		
		immutableBuilder
				.status(persistable.getStatus())
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate());
		
		return immutableBuilder.build();
	}

	public static boolean immutableEqualsToEntity(TechnicalUser immutable, PTechnicalUser persistable)
	{
		return immutable.getTechUserNumber().equals(persistable.getTechUserNumber());
	}
}
