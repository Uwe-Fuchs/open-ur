package org.openur.module.persistence.rdbms.entity.userstructure;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser.TechnicalUserBuilder;

public class TechnicalUserMapper
{
	public static PTechnicalUser mapFromImmutable(TechnicalUser immutable)
	{
		PTechnicalUser persistable = new PTechnicalUser();
		
		persistable.setTechUserNumber(immutable.getTechUserNumber());
		persistable.setStatus(immutable.getStatus());
		
		return persistable;
	}
	
	public static TechnicalUser mapFromEntity(PTechnicalUser persistable)
	{
		TechnicalUserBuilder immutableBuilder = new TechnicalUserBuilder(persistable.getTechUserNumber());
		
		if (StringUtils.isNotEmpty(persistable.getIdentifier()))
		{
			immutableBuilder = new TechnicalUserBuilder(persistable.getIdentifier());
		}
		
		immutableBuilder
				.status(persistable.getStatus())
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate());
		
		return immutableBuilder.build();
	}
	
	public static boolean immutableEqualsToEntity(TechnicalUser immutable, PTechnicalUser persistable)
	{
		if (!UserStructureBaseMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getTechUserNumber(), persistable.getTechUserNumber())
				.isEquals();
	}
}
