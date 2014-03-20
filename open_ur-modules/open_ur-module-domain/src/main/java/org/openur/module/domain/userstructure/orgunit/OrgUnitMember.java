package org.openur.module.domain.userstructure.orgunit;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
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
	private final IOrganizationalUnit orgUnit;
	private final Set<IRole> roles;

	// constructors:
	public OrgUnitMember(OrgUnitMemberBuilder b)
	{
		super();

		this.person = b.getPerson();
		this.orgUnit = b.getOrgUnit();
		this.roles = Collections.unmodifiableSet(b.getRoles());
	}

	// accessors:
	@Override
	public IPerson getPerson()
	{
		return person;
	}

	@Override
	public IOrganizationalUnit getOrgUnit()
	{
		return orgUnit;
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
			if (role.getPermissions(app) != null && role.getPermissions(app).contains(permission))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int compareTo(IOrgUnitMember other)
	{
		if (this.equals(other))
		{
			return 0;
		}
		
		return new CompareToBuilder()
										.append(this.getOrgUnit(), other.getOrgUnit())
										.append(this.getPerson(), other.getPerson())
										.toComparison();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof IOrgUnitMember))
		{
			return false;
		}

		IOrgUnitMember other = (IOrgUnitMember) obj;
		
		return new EqualsBuilder()
										.append(this.getPerson(), other.getPerson())
										.append(this.getOrgUnit(), other.getOrgUnit())
										.isEquals();
	}
}
