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
	POrgUnitMember mapFromDomainObject(M domainObject, POrganizationalUnit pOrgUnit);

	M mapFromEntity(POrgUnitMember entity, String orgUnitId, boolean inclRoles);
}