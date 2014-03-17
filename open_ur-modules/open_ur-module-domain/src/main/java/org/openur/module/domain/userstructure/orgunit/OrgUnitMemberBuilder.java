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
	private Set<IRole> roles = null;

	public OrgUnitMemberBuilder(IPerson person)
	{
		init(person, null);
	}

	public OrgUnitMemberBuilder(IPerson person, Collection<IRole> roles)
	{
		init(person, roles);
	}

	private void init(IPerson person, Collection<IRole> roles)
	{
		Validate.notNull(person, "person must not be null!");
		this.person = person;

		Set<IRole> roleSet = new HashSet<IRole>();

		if (roles != null)
		{
			roleSet.addAll(roles);
		}

		this.roles = roleSet;
	}

	IPerson getPerson()
	{
		return person;
	}

	Set<IRole> getRoles()
	{
		return roles;
	}
}
