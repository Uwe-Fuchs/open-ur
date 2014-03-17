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
import org.openur.module.domain.userstructure.user.person.IPerson;

public class OrgUnitMember
	extends GraphNode
	implements IOrgUnitMember
{
	private static final long serialVersionUID = -8299862057665830750L;

	// properties:
	private final IPerson person;
	private final Set<IRole> roles;

	// constructors:
	public OrgUnitMember(OrgUnitMemberBuilder b)
	{
		super();

		this.person = b.getPerson();
		this.roles = Collections.unmodifiableSet(b.getRoles());
	}
	
	public OrgUnitMember(IPerson person, Collection<IRole> roles)
	{
		super();
		
		Validate.notNull(person, "person must not be null!");	
		this.person = person;
		
		Set<IRole> r = new HashSet<IRole>();
		
		if (roles != null)
		{
			r.addAll(roles);
		}
		
		this.roles = Collections.unmodifiableSet(r);
	}

	// accessors:
	@Override
	public IPerson getPerson()
	{
		return person;
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
		return this.getPerson().compareTo(other.getPerson());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof IOrgUnitMember))
		{
			return false;
		}

		IOrgUnitMember other = (IOrgUnitMember) obj;

		return this.getPerson().equals(other.getPerson());
	}
}
