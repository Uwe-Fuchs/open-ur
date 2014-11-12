package org.openur.module.domain.userstructure.orgunit.abstr;

import org.openur.module.domain.GraphNode;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.person.abstr.AbstractPerson;

public abstract class AbstractOrgUnitMember
	extends GraphNode
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -8299862057665830750L;

	// properties:
	private final AbstractPerson person;
	private final String orgUnitId;

	// constructors:
	protected AbstractOrgUnitMember(AbstractPerson person, String orgUnitId)
	{
		super();

		this.person = person;
		this.orgUnitId = orgUnitId;
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
}
