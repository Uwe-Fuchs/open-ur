package org.openur.module.domain.utils.common;

import java.util.Collection;

import org.openur.module.domain.IdentifiableEntityImpl;

public class DomainObjectHelper
{
	public static <I extends IdentifiableEntityImpl> I findIdentifiableEntityInCollection(Collection<I> coll, String identifier)
	{
		for (I obj : coll)
		{
			if (obj.getIdentifier().equals(identifier))
			{
				return obj;
			}
		}
		
		return null;
	}
}