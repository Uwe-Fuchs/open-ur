package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitBuilder;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.util.exception.UnsupportedException;

/**
 * use this for saving {@link IOrganizationalUnit}-objetcs within
 * {@link IOrgUnitMember}-objects.
 */
public class OrgUnitDelegate
	extends AbstractOrgUnit
{
	private static final long serialVersionUID = 3368895151661296646L;

	public OrgUnitDelegate(
		AbstractOrgUnitBuilder<? extends AbstractOrgUnitBuilder<?>> b)
	{
		super(b);
	}

	@Override
	public Set<IOrgUnitMember> getMembers()
	{
		throw new UnsupportedException("Not supported in Delegate!");
	}

	@Override
	public IOrgUnitMember findMember(String id)
	{
		throw new UnsupportedException("Not supported in Delegate!");
	}

	@Override
	public boolean isMember(String id)
	{
		throw new UnsupportedException("Not supported in Delegate!");
	}

	@Override
	public boolean isMember(IPerson person)
	{
		throw new UnsupportedException("Not supported in Delegate!");
	}

	@Override
	public IOrgUnitMember findMember(IPerson person)
	{
		throw new UnsupportedException("Not supported in Delegate!");
	}
}
