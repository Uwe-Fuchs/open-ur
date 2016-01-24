package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <M> placeholder for member-domain-object.
 */
public interface IOrgUnitMemberMapper<M extends IAuthorizableMember>
{
	/**
	 * map entity on corresponding domain-object-class plus corresponding org-unit-entity.
	 * 
	 * @param domainObject
	 * @param pOrgUnit
	 * 
	 * @return entity-instance.
	 */
	POrgUnitMember mapFromDomainObject(M domainObject, POrganizationalUnit pOrgUnit);

	/**
	 * map entity on corresponding domain-object-class plus ID of corresponding org-unit,
	 * indicate if including members and roles.
	 * 
	 * @param entity
	 * @param orgUnitId
	 * @param inclRoles
	 * 
	 * @return (immutable) domain-object.
	 */
	M mapFromEntity(POrgUnitMember entity, String orgUnitId, boolean inclRoles);
}