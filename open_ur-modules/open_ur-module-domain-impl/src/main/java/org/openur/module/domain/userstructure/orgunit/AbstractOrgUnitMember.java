package org.openur.module.domain.userstructure.orgunit;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.userstructure.person.Person;

/**
 * Implementation of {@link IOrgUnitMember}. All userstructure-related concerns are implemented. Anyway the
 * security-related concerns are still missing, so the final (and concrete) implementation is found under 
 * {@link AuthorizableMember}.
 * 
 * @author info@uwefuchs.com
 */
public abstract class AbstractOrgUnitMember
	extends IdentifiableEntityImpl
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -8299862057665830750L;

	// properties:
	private final Person person;
	private final String orgUnitId;

	// constructors:
	protected AbstractOrgUnitMember(AbstractOrgUnitMemberBuilder<? extends AbstractOrgUnitMemberBuilder<?>> b)
	{
		super(b);

		this.person = b.person;
		this.orgUnitId = b.orgUnitId;
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

	// operations:
	@Override
	public boolean equals(Object obj)
	{
		return isEqual(obj);
	}
	
	// builder-class:
	public static abstract class AbstractOrgUnitMemberBuilder<T extends AbstractOrgUnitMemberBuilder<T>>
		extends IdentifiableEntityBuilder<T>
	{
		// properties:
		private Person person = null;
		private String orgUnitId = null;
		
		// constructor:
		protected AbstractOrgUnitMemberBuilder(Person person, String orgUnitId)
		{
			super();
			
			Validate.notNull(person, "person must not be null!");
			Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");

			this.person = person;
			this.orgUnitId = orgUnitId;		
		}
		
		// accessors:
		protected Person getPerson()
		{
			return person;
		}

		protected String getOrgUnitId()
		{
			return orgUnitId;
		}

		// builder:
		protected abstract AbstractOrgUnitMember build();
	}
}
