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
	private IOrganizationalUnit orgUnit = null;
	private Set<IRole> roles = new HashSet<IRole>();

	public OrgUnitMemberBuilder(IPerson person, IOrganizationalUnit orgUnit)
	{
		Validate.notNull(person, "person must not be null!");
		Validate.notNull(orgUnit, "org-unit must not be null!");
		this.person = person;
		this.orgUnit = orgUnit;
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

	// accessors:
	IPerson getPerson()
	{
		return person;
	}

	IOrganizationalUnit getOrgUnit()
	{
		return orgUnit;
	}

	Set<IRole> getRoles()
	{
		return roles;
	}
}
