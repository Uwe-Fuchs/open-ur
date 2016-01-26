package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.springframework.data.domain.Persistable;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <PO> generic placeholder for org-unit-entity.
 * @param <PM> generic placeholder for org-unit-member-entity.
 * @param <M> generic palceholder for domain-object-type.
 */
public interface IOrgUnitMemberMapper<PO extends Persistable<Long>, PM extends Persistable<Long>, M extends IAuthorizableMember>
{
	/**
	 * map domain-object plus corresponding org-unit-entity on corresponding org-unit-member-entity.
	 * 
	 * @param domainObject
	 * @param pOrgUnit
	 * 
	 * @return entity-instance.
	 */
	PM mapFromDomainObject(M domainObject, PO pOrgUnit);

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
	M mapFromEntity(PM entity, String orgUnitId, boolean inclRoles);
}