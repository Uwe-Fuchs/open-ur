package org.openur.module.domain.utils.compare;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.utils.common.DomainObjectHelper;

public class AuthorizableMemberComparer
	extends AbstractIdentifiableObjectComparer<AuthorizableMember>
{
	@Override
	protected boolean internalEqualityCheck(AuthorizableMember member1, AuthorizableMember member2)
	{
		boolean isEqual = EqualsBuilder.reflectionEquals(member1, member2, Arrays.asList("person", "roles"));
		
		if (!isEqual)
		{
			return false;
		}
		
		isEqual = new PersonComparer().objectsAreEqual(member1.getPerson(), member2.getPerson());
		
		if (!isEqual)
		{
			return false;
		}
		
		RoleComparer roleComparer = new RoleComparer();
		
		for (OpenURRole role1 : member1.getRoles())
		{
			OpenURRole role2 = DomainObjectHelper.findIdentifiableEntityInCollection(member2.getRoles(), role1.getIdentifier());
			
			if (role2 == null || !roleComparer.objectsAreEqual(role1, role2))
			{
				return false;
			}
		}
		
		return true;
	}
}
