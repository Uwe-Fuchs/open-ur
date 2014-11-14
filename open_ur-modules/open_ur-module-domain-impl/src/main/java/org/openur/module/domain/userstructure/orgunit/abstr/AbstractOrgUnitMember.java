package org.openur.module.domain.userstructure.orgunit.abstr;

import java.time.LocalDateTime;

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
	protected AbstractOrgUnitMember(AbstractPerson person, String orgUnitId)
	{
		super(new OrgUnitBuilderImpl());
		
		validateFields(person, orgUnitId);

		this.person = person;
		this.orgUnitId = orgUnitId;		
	}
	
	protected AbstractOrgUnitMember(AbstractPerson person, String orgUnitId, LocalDateTime creationDate)
	{
		super(new OrgUnitBuilderImpl(creationDate));
		
		validateFields(person, orgUnitId);

		this.person = person;
		this.orgUnitId = orgUnitId;		
	}
	
	private void validateFields(AbstractPerson person, String orgUnitId)
	{		
		Validate.notNull(person, "person must not be null!");
		Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");
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
	private static class OrgUnitBuilderImpl
		extends IdentifiableEntityBuilder<OrgUnitBuilderImpl>
	{
		public OrgUnitBuilderImpl()
		{
			super();
		}
		
		public OrgUnitBuilderImpl(LocalDateTime creationDate)
		{
			super(creationDate);
		}
	}
}
