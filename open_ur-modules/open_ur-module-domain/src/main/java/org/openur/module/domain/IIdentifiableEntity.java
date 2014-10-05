package org.openur.module.domain;

import java.time.LocalDateTime;

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
	 * @return LocalDateTime
	 */
	LocalDateTime getLastModifiedDate();
	
	/**
	 * the date or timestamp of creating this entity.
	 * 
	 * @return LocalDateTime.
	 */
	LocalDateTime getCreationDate();
	
	/**
	 * identity-objects should be equal if they have the same identifier.
	 * criteria for equality should be the identifier.
	 * 
	 * @param obj the object that is checked in terms of equality.
	 * 
	 * @return both objects are equal.
	 */
	boolean equals(Object obj);
}
