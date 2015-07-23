package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.application.IApplication;
import org.openur.module.persistence.rdbms.entity.PApplication;

/**
 * maps entity-data from and to domain-object.
 * 
 * @author info@uwefuchs.com
 *
 * @param <A> placeholder for application-domain-object.
 */
public interface IApplicationMapper<A extends IApplication>
{
	PApplication mapFromDomainObject(A domainObject);

	A mapFromEntity(PApplication entity);
}