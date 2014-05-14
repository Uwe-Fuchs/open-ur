package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.userstructure.user.person.IPerson;

public class OrgUnitMemberBuilder
{
	// properties:
	private IPerson person = null;
	private String orgUnitId = null;
	private Set<IRole> roles = new HashSet<IRole>();

	public OrgUnitMemberBuilder(IPerson person, String orgUnitId)
	{
		Validate.notNull(person, "person must not be null!");
		Validate.notNull(orgUnitId, "org-unit must not be null!");
		this.person = person;
		this.orgUnitId = orgUnitId;
	}
	
	// builder-methods:
	public OrgUnitMemberBuilder roles(Collection<IRole> roles)
	{
		if (roles != null)
		{
			this.roles.addAll(roles);
		}
		
		return this;
	}
	
	public OrgUnitMember build()
	{
		return new OrgUnitMember(this);
	}

	// accessors:
	IPerson getPerson()
	{
		return person;
	}

	String getOrgUnitId()
	{
		return orgUnitId;
	}

	Set<IRole> getRoles()
	{
		return roles;
	}
}
