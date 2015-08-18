package org.openur.module.domain.utils.compare;

import org.openur.module.domain.IdentifiableEntityImpl;

/**
 * checks if two given objects of type {@link IdentifiableEntityImpl} are equal.
 * 
 * @author info@uwefuchs.com
 *
 * @param <I>
 */
@FunctionalInterface
public interface IIdentifiableEntityComparer<I extends IdentifiableEntityImpl>
{
	boolean objectsAreEqual(I entity1, I entity2);
}
