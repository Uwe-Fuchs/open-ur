package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.GraphNode;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;

public class OrgUnitMember
	extends GraphNode
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -8299862057665830750L;

	// properties:
	private final String personId;
	private final Set<IRole> roles;

	// constructors:
	public OrgUnitMember(String personId)
	{
		super();
		
		Validate.notEmpty(personId, "person-id must not be empty!");	
		this.personId = personId;
		this.roles = null;
	}
	
	public OrgUnitMember(String personId, Collection<IRole> roles)
	{
		super();
		
		Validate.notEmpty(personId, "person-id must not be empty!");	
		this.personId = personId;
		
		Set<IRole> r = new HashSet<IRole>();
		
		if (roles != null)
		{
			r.addAll(roles);
		}
		
		this.roles = Collections.unmodifiableSet(r);
	}

	// accessors:
	@Override
	public String getPersonId()
	{
		return personId;
	}

	@Override
	public Set<IRole> getRoles()
	{
		return roles;
	}

	// operations:
	@Override
	public boolean hasRole(IRole role)
	{
		return getRoles().contains(role);
	}

	@Override
	public boolean hasPermission(IApplication app, IPermission permission)
	{
		for (IRole role : getRoles())
		{
			if (role.getPermissions(app).contains(permission))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int compareTo(IOrgUnitMember other)
	{
		return this.getPersonId().compareTo(other.getPersonId());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof IOrgUnitMember))
		{
			return false;
		}

		IOrgUnitMember other = (IOrgUnitMember) obj;

		return this.getPersonId().equals(other.getPersonId());
	}
}
