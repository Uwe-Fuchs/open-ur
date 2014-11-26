package org.openur.module.domain.security.authorization;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitMember;
import org.openur.module.domain.userstructure.person.abstr.AbstractPerson;

public class AuthorizableMember
	extends AbstractOrgUnitMember
	implements IAuthorizableMember
{
	private static final long serialVersionUID = 1090300386847874882L;
	
	private final Set<OpenURRole> roles;

	// constructor:
	public AuthorizableMember(AuthorizableMemberBuilder b)
	{
		super(b);
		
		this.roles = Collections.unmodifiableSet(b.roles);
	}

	// accessors:
	@Override
	public Set<OpenURRole> getRoles()
	{
		return roles;
	}
	
	// builder-class:
	public static class AuthorizableMemberBuilder
		extends AbstractOrgUnitMemberBuilder<AuthorizableMemberBuilder>
	{
		// properties:
		private Set<OpenURRole> roles = new HashSet<>();
		
		// constructor:
		public AuthorizableMemberBuilder(AbstractPerson person, String orgUnitId)
		{
			super(person, orgUnitId);
		}
		
		// builder-methods:
		public AuthorizableMemberBuilder roles(Collection<OpenURRole> roles)
		{
			Validate.notEmpty(roles, "roles-list must not be empty!");		
			this.roles.addAll(roles);
			
			return this;
		}
		
		public AuthorizableMemberBuilder addRole(OpenURRole role)
		{
			Validate.notNull(role, "role must not be null!");
			this.roles.add(role);
			
			return this;
		}

		// builder:
		@Override
		public AuthorizableMember build()
		{
			return new AuthorizableMember(this);
		}
	}
}
