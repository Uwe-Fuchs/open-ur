package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser.TechnicalUserBuilder;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;

public class TechnicalUserMapper
	extends UserStructureBaseMapper
{
	public PTechnicalUser mapFromImmutable(TechnicalUser immutable)
	{
		PTechnicalUser persistable = new PTechnicalUser(immutable.getTechUserNumber());

		persistable.setStatus(immutable.getStatus());
		
		return persistable;
	}
	
	public TechnicalUser mapFromEntity(PTechnicalUser persistable)
	{
		TechnicalUserBuilder immutableBuilder = new TechnicalUserBuilder(persistable.getTechUserNumber());		
		immutableBuilder = super.buildImmutable(immutableBuilder, persistable);
		
		return immutableBuilder.build();
	}

	public static boolean immutableEqualsToEntity(TechnicalUser immutable, PTechnicalUser persistable)
	{
		if (!UserStructureBaseMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		return immutable.getTechUserNumber().equals(persistable.getTechUserNumber());
	}
}
