package org.openur.module.domain.userstructure;

import org.openur.module.domain.IIdentifiableEntity;

/**
 * @author uwe@uwefuchs.com
 */
public interface IUserStructureBase
	extends IIdentifiableEntity
{
	/**
	 * get the domain specific number (which nust not be identical with the technical id
	 * such as a database primary key etc).
	 * The number is not mandatory, thus the result may be null.
	 * 
	 * @return the number as String or null if no number is set.
	 */
	String getNumber();
	
	/**
	 * the status of the userstructure-entity, such as active or inactive.
	 * 
	 * @return the status
	 */
	Status getStatus();
}
