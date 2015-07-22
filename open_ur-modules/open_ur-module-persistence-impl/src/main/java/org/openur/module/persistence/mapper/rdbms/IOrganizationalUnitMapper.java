package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <O> placeholder for orgUnit-domain-object.
 */
public interface IOrganizationalUnitMapper<O extends IAuthorizableOrgUnit>
{
	POrganizationalUnit mapFromDomainObject(O domainObject);

	O mapFromEntity(POrganizationalUnit entity, boolean inclMembers, boolean inclRoles);
}