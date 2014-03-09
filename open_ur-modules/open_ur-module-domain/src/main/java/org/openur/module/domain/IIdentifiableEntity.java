package org.openur.module.domain;

import java.util.Date;

public interface IIdentifiableEntity
{
	/**
	 * the literal which uniquely identifies the entity.
	 * 
	 * @return identifier.
	 */
	String getIdentifier();
	
	/**
	 * the date or timestamp of changing this entity (may be empty).
	 * 
	 * @return date.
	 */
	Date getChangeDate();
	
	/**
	 * the date or timestamp of creating this entity.
	 * 
	 * @return date.
	 */
	Date getCreationDate();
	
	/**
	 * identity-objects should be comparable.
	 * criteria for equality should be the identifier.
	 * 
	 * @param obj the object that is checked on equality.
	 * 
	 * @return both objects are equal.
	 */
	boolean equals(Object obj);
}
