package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 * 
 * @param <M> generic palceholder for domain-object-type.
 */
public interface IOrgUnitMemberMapper<M extends IAuthorizableMember>
{
	/**
	 * map domain-object plus corresponding org-unit-entity on corresponding org-unit-member-entity.
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