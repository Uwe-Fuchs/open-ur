package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.persistence.rdbms.entity.PRole;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <R> placeholder for role-domain-object.
 */
public interface IRoleMapper<R extends IRole>
{
	PRole mapFromDomainObject(R domainObject);

	R mapFromEntity(PRole entity);
}