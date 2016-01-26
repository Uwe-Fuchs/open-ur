package org.openur.module.domain.userstructure;

import org.openur.module.domain.IIdentifiableEntity;
import org.openur.module.util.data.Status;

/**
 * @author uwe@uwefuchs.com
 */
public interface IUserStructureBase
	extends IIdentifiableEntity
{
	/**
	 * get the domain specific number (which nust not be identical with the technical id
	 * such as a database primary key etc).
	 * The number should always be set, i.e. never be null.
	 * 
	 * @return the number as String.
	 */
	String getNumber();
	
	/**
	 * the {@link Status} of the userstructure-entity, such as active or inactive.
	 * 
	 * @return the {@link Status}.
	 */
	Status getStatus();
}
