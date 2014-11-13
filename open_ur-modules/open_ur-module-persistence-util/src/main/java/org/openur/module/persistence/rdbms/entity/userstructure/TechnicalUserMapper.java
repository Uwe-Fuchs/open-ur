package org.openur.module.persistence.rdbms.entity.userstructure;

import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUserBuilder;

public class TechnicalUserMapper
{
	public static PTechnicalUser mapFromImmutable(TechnicalUser immutable)
	{
		PTechnicalUser persistable = new PTechnicalUser();
		
		persistable.setNumber(immutable.getNumber());
		persistable.setStatus(immutable.getStatus());
		
		return persistable;
	}
	
	public static TechnicalUser mapFromEntity(PTechnicalUser persistable)
	{
		TechnicalUserBuilder builder = new TechnicalUserBuilder()
				.number(persistable.getNumber())
				.status(persistable.getStatus())
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate());
		
		return builder.build();
	}
	
	public static boolean immutableEqualsToEntity(TechnicalUser immutable, PTechnicalUser persistable)
	{
		return UserStructureBaseMapper.immutableEqualsToEntity(immutable, persistable);
	}
}
