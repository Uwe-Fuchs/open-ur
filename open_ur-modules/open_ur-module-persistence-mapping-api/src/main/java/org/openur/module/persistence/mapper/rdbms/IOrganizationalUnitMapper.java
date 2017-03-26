package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <O> placeholder for orgUnit-domain-object.
 */
public interface IOrganizationalUnitMapper<O extends IOrganizationalUnit>
	extends IEntityDomainObjectMapper<POrganizationalUnit, O>
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
	O mapFromEntity(POrganizationalUnit entity, boolean inclMembers, boolean inclRoles);
}