package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser.TechnicalUserBuilder;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;

public class TechnicalUserMapper
	extends UserStructureBaseMapper implements ITechnicalUserMapper<TechnicalUser>
{
	@Override
	public PTechnicalUser mapFromDomainObject(TechnicalUser domainObject)
	{
		PTechnicalUser persistable = new PTechnicalUser(domainObject.getTechUserNumber());

		persistable.setStatus(domainObject.getStatus());
		
		return persistable;
	}
	
	@Override
	public TechnicalUser mapFromEntity(PTechnicalUser entity)
	{
		TechnicalUserBuilder immutableBuilder = new TechnicalUserBuilder(entity.getTechUserNumber());		
		immutableBuilder = super.mapFromEntity(immutableBuilder, entity);
		
		return immutableBuilder.build();
	}

	public static boolean domainObjectEqualsToEntity(TechnicalUser domainObject, PTechnicalUser entity)
	{
		if (!UserStructureBaseMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		return domainObject.getTechUserNumber().equals(entity.getTechUserNumber());
	}
}
