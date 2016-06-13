package org.openur.module.domain.userstructure.technicaluser;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.IUserStructureBase;

/**
 * 
 * @author uwe
 *
 */
public interface ITechnicalUser
	extends IUserStructureBase, Comparable<ITechnicalUser>
{	
	// operations:
	/**
	 * returns the (domain-specific) number of this tech-user.
	 */
	default String getTechUserNumber()
	{
		String number = getNumber();
		Validate.notNull(number, "technical-user-number must not be null!");		
		
		return number;
	}
	
	default int compareTo(ITechnicalUser other)
	{
		int comparison = new CompareToBuilder()
				.append(this.getTechUserNumber(), other.getTechUserNumber())
				.append(this.getStatus(), other.getStatus())
				.toComparison();

		return comparison;
	}
}