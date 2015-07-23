package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.persistence.rdbms.entity.PPermission;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <P> placeholder for permission-domain-object.
 */
public interface IPermissionMapper<P extends IPermission>
{
	PPermission mapFromDomainObject(P domainObject);

	P mapFromEntity(PPermission entity);
}