package org.openur.module.domain.userstructure.orgunit;


import org.openur.module.domain.userstructure.user.person.IPerson;

public interface IOrgUnitMember
	extends Comparable<IOrgUnitMember>
{
	/**
	 * returns the person-object of the member.
	 */
	IPerson getPerson();
	
	/**
	 * returns the org-unit of the member.
	 */
	String getOrgUnitId();
}
