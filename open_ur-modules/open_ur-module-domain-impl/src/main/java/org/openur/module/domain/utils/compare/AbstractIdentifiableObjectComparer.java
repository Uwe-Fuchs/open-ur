package org.openur.module.domain.utils.compare;

import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.util.exception.OpenURRuntimeException;

public abstract class AbstractIdentifiableObjectComparer<I extends IdentifiableEntityImpl>
	implements IIdentifiableEntityComparer<I>
{
	@Override
	public boolean objectsAreEqual(I entity1, I entity2)
	{
		if (entity1 == null && entity2 == null)
		{
			throw new OpenURRuntimeException("Both entities are null!");
		}

		if (entity1 == null || entity2 == null)
		{
			return false;
		}
		
		return internalEqualityCheck(entity1, entity2);
	}

	protected abstract boolean internalEqualityCheck(I entity1, I entity2);
}
