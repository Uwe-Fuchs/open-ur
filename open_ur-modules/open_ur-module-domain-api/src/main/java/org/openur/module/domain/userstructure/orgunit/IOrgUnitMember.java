package org.openur.module.domain.userstructure.orgunit;


import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.IIdentifiableEntity;
import org.openur.module.domain.userstructure.person.IPerson;

public interface IOrgUnitMember
	extends IIdentifiableEntity, Comparable<IOrgUnitMember>
{
	/**
	 * returns the person-object of the member.
	 */
	IPerson getPerson();
	
	/**
	 * returns the org-unit of the member.
	 */
	String getOrgUnitId();

	// operations:
	default int compareTo(IOrgUnitMember other)
	{
		if (this.equals(other))
		{
			return 0;
		}
		
		return new CompareToBuilder()
										.append(this.getOrgUnitId(), other.getOrgUnitId())
										.append(this.getPerson(), other.getPerson())
										.toComparison();
	}
}
