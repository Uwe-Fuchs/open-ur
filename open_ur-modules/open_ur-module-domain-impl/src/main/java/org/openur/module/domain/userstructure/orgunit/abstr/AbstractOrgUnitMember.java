package org.openur.module.domain.userstructure.orgunit.abstr;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.person.abstr.AbstractPerson;

public abstract class AbstractOrgUnitMember
	extends IdentifiableEntityImpl
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -8299862057665830750L;

	// properties:
	private final AbstractPerson person;
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
	public AbstractPerson getPerson()
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
		private AbstractPerson person = null;
		private String orgUnitId = null;
		
		// constructor:
		protected AbstractOrgUnitMemberBuilder(AbstractPerson person, String orgUnitId)
		{
			super();
			
			Validate.notNull(person, "person must not be null!");
			Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");

			this.person = person;
			this.orgUnitId = orgUnitId;		
		}
		
		// builder:
		protected abstract AbstractOrgUnitMember build();
	}
}
