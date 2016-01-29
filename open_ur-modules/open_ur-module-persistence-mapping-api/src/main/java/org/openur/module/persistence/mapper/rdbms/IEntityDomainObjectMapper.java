package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.IIdentifiableEntity;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;

/**
 * mapping data between PA-Entities and (immutable) Open-UR-Domain-Objects.
 * 
 * @author info@uwefuchs.com
 *
 * @param <P> generic placeholder for entity-type.
 * @param <I> generic palceholder for domain-object-type.
 */
public interface IEntityDomainObjectMapper<P extends AbstractOpenUrPersistable, I extends IIdentifiableEntity>
{
	/**
	 * map domain-object on corresponding entity-class.
	 * 
	 * @param domainObject
	 * 
	 * @return entity-instance.
	 */
	P mapFromDomainObject(I domainObject);
	
	/**
	 * map entity on corresponding domain-object-class.
	 * 
	 * @param entity
	 * 
	 * @return (immutable) domain-object.
	 */
	I mapFromEntity(P entity);
}
