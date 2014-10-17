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

	// operations:
	/**
	 * Identity-objects should be equal if they have the same identifier. 
	 * Implementations of this interface should use this method when overriding the {@code equals}-method in {@link GraphNode}.
	 * 
	 * @param obj the object that is checked in terms of equality.
	 * 
	 * @return both objects are equal.
	 */
	default boolean isEqual(Object obj)
	{
		if (obj == null || !this.getClass().equals(obj.getClass()))
		{
			return false;
		}
		
		if (this == obj)
		{
			return true;
		}

		IIdentifiableEntity ie = (IIdentifiableEntity) obj;
		
		return this.getIdentifier().equals(ie.getIdentifier());
	}
}
