package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.springframework.data.domain.Persistable;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <O> placeholder for orgUnit-domain-object.
 */
public interface IOrganizationalUnitMapper<P extends Persistable<Long>, O extends IAuthorizableOrgUnit>
	extends IEntityDomainObjectMapper<P, O>
{
	/**
	 * map entity on corresponding domain-object-class, indicate if including members and roles.
	 * 
	 * @param entity
	 * @param inclMembers
	 * @param inclRoles
	 * 
	 * @return (immutable) domain-object.
	 */
	O mapFromEntity(P entity, boolean inclMembers, boolean inclRoles);
}