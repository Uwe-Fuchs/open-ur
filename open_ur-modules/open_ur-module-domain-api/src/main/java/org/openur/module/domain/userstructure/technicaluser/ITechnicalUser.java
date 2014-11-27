package org.openur.module.domain.userstructure.technicaluser;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.IUserStructureBase;

public interface ITechnicalUser
	extends IUserStructureBase, Comparable<ITechnicalUser>
{
	// operations:
	/**
	 * returns the (domain-specific) number of this tech-user.
	 */
	default String getTechUserNumber()
	{
		return getNumber();
	}
	
	default int compareTo(ITechnicalUser other)
	{
		int comparison = new CompareToBuilder()
				.append(this.getNumber(), other.getNumber())
				.append(this.getStatus(), other.getStatus())
				.toComparison();

		return comparison;
	}
}
