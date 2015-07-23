package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <T> placeholder for technicalUser-domain-object.
 */
public interface ITechnicalUserMapper<T extends ITechnicalUser>
{
	PTechnicalUser mapFromDomainObject(T domainObject);

	T mapFromEntity(PTechnicalUser entity);
}