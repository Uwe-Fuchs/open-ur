package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;

import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitBuilder;
import org.openur.module.util.exception.UnsupportedException;

public class OrgUnitDelegateBuilder
	extends AbstractOrgUnitBuilder<OrgUnitDelegateBuilder>
{
	public OrgUnitDelegateBuilder(String identifier)
	{
		super(identifier);
	}
	
	public OrgUnitDelegate build()
	{
		return new OrgUnitDelegate(this);
	}

	@Override
	public OrgUnitDelegateBuilder members(Collection<IOrgUnitMember> members)
	{
		throw new UnsupportedException("Not supported in Delegate!");
	}
}
