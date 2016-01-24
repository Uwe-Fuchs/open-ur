package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.persistence.rdbms.entity.PPerson;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <P> placeholder for person-domain-object.
 */
public interface IPersonMapper<P extends IPerson>
{
	PPerson mapFromDomainObject(P domainObject);

	P mapFromEntity(PPerson entity);
}