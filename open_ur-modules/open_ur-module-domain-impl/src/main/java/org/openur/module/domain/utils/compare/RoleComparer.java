package org.openur.module.domain.utils.compare;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.utils.common.DomainObjectHelper;

public class RoleComparer
	extends AbstractIdentifiableObjectComparer<OpenURRole>
{
	@Override
	protected boolean internalEqualityCheck(OpenURRole role1, OpenURRole role2)
	{
		boolean isEqual = EqualsBuilder.reflectionEquals(role1, role2, "permissions");
		
		if (!isEqual)
		{
			return false;
		}
		
		for (OpenURApplication app1 : role1.getAllPermissions().keySet())
		{
			OpenURApplication app2 = DomainObjectHelper.findIdentifiableEntityInCollection(role2.getAllPermissions().keySet(), app1.getIdentifier());
			
			if (app2 == null)
			{
				return false;
			}
			
			PermissionComparer permissionComparer = new PermissionComparer();
			
			for (OpenURPermission perm1 : role1.getPermissions(app1))
			{
				OpenURPermission perm2 = DomainObjectHelper.findIdentifiableEntityInCollection(role2.getPermissions(app2), perm1.getIdentifier());
				
				if (perm2 == null || !permissionComparer.objectsAreEqual(perm1, perm2))
				{
					return false;
				}
			}
		}
		
		return true;
	}
}
