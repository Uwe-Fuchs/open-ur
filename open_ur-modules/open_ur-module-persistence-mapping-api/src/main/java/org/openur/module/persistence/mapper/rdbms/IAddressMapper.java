package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.IAddress;
import org.openur.module.persistence.rdbms.entity.PAddress;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <A> placeholder for address-domain-object.
 */
public interface IAddressMapper<A extends IAddress>
{
	PAddress mapFromDomainObject(A domainObject);

	A mapFromEntity(PAddress entity);
}