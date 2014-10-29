package org.openur.module.domain.userstructure.technicaluser;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.IUserStructureBase;

public interface ITechnicalUser
	extends IUserStructureBase, Comparable<ITechnicalUser>
{
	// operations:
	default int compareTo(ITechnicalUser other)
	{
		int comparison = new CompareToBuilder()
				.append(this.getNumber(), other.getNumber())
				.append(this.getStatus(), other.getStatus())
				.toComparison();

		return comparison;
	}
}
