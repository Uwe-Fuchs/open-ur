package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.person.Person;

/**
 * Implementation of {@link IOrgUnitMember}. All userstructure-related concerns are implemented.
 * 
 * @author info@uwefuchs.com
 */
public class OrgUnitMember
	extends IdentifiableEntityImpl
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -8299862057665830750L;

	// properties:
	private final Person person;
	private final String orgUnitId;
	private final Set<OpenURRole> roles;

	// constructors:
	public OrgUnitMember(OrgUnitMemberBuilder b)
	{
		super(b);

		this.person = b.person;
		this.orgUnitId = b.orgUnitId;
		this.roles = Collections.unmodifiableSet(b.roles);
	}

	// accessors:
	@Override
	public Person getPerson()
	{
		return person;
	}

	@Override
	public String getOrgUnitId()
	{
		return orgUnitId;
	}
	
	@Override
	public Set<OpenURRole> getRoles()
	{
		return roles;
	}

	// operations:
	@Override
	public boolean equals(Object obj)
	{
		return isEqual(obj);
	}
	
	// builder-class:
	public static class OrgUnitMemberBuilder
		extends IdentifiableEntityBuilder<OrgUnitMemberBuilder>
	{
		// properties:
		private Person person = null;
		private String orgUnitId = null;
		private Set<OpenURRole> roles = new HashSet<>();
		
		// constructor:
		public OrgUnitMemberBuilder(Person person, String orgUnitId)
		{
			super();
			
			Validate.notNull(person, "person must not be null!");
			Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");

			this.person = person;
			this.orgUnitId = orgUnitId;	
		}
		
		// accessors:
		public Person getPerson()
		{
			return person;
		}

		public String getOrgUnitId()
		{
			return orgUnitId;
		}
		
		// builder-methods:
		public OrgUnitMemberBuilder roles(Collection<OpenURRole> roles)
		{
			Validate.notEmpty(roles, "roles-list must not be empty!");		
			this.roles.addAll(roles);
			
			return this;
		}
		
		public OrgUnitMemberBuilder addRole(OpenURRole role)
		{
			Validate.notNull(role, "role must not be null!");
			this.roles.add(role);
			
			return this;
		}

		// builder:
		public OrgUnitMember build() {
			return new OrgUnitMember(this);
		}
	}
}
