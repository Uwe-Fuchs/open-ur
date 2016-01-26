package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.security.authentication.IUserAccount;
import org.openur.module.domain.userstructure.IUserStructureBase;
import org.springframework.data.domain.Persistable;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <P> generic placeholder for user-account-entity.
 * @param <U> generic palceholder for domain-object-type.
 * @param <UB> generic placeholder for userstructure-base-entity.
 */
public interface IUserAccountMapper<P extends Persistable<Long>, U extends IUserAccount, UB extends IUserStructureBase>
{
	/**
	 * map domain-object plus corresponding userstructure-base-domain-object on corresponding entity.
	 * 
	 * @param domainObject
	 * @param identifier
	 * 
	 * @return entity-instance.
	 */
	P mapFromDomainObject(U userAccount, UB userStructureBase);

	/**
	 * map entity on corresponding domain-object-class.
	 * 
	 * @param entity
	 * 
	 * @return (immutable) domain-object.
	 */
	U mapFromEntity(P entity);
}
