package org.openur.module.domain.userstructure.orgunit;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.GraphNode;
import org.openur.module.domain.userstructure.person.IPerson;

public class OrgUnitMember
	extends GraphNode
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -8299862057665830750L;

	// properties:
	private final IPerson person;
	private final String orgUnitId;

	// constructors:
	public OrgUnitMember(IPerson person, String orgUnitId)
	{
		super();

		this.person = person;
		this.orgUnitId = orgUnitId;
	}

	// accessors:
	@Override
	public IPerson getPerson()
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
		if (!(obj instanceof IOrgUnitMember))
		{
			return false;
		}

		IOrgUnitMember other = (IOrgUnitMember) obj;
		
		return new EqualsBuilder()
										.append(this.getPerson(), other.getPerson())
										.append(this.getOrgUnitId(), other.getOrgUnitId())
										.isEquals();
	}
}
