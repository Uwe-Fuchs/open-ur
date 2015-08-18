package org.openur.module.domain.utils.compare;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;

public class PermissionComparer
	extends AbstractIdentifiableObjectComparer<OpenURPermission>
{
	@Override
	protected boolean internalEqualityCheck(OpenURPermission perm1, OpenURPermission perm2)
	{
		boolean isEqual = EqualsBuilder.reflectionEquals(perm1, perm2, "application");
		
		if (!isEqual)
		{
			return false;
		}
			
		return EqualsBuilder.reflectionEquals(perm1.getApplication(), perm2.getApplication());
	}
}
