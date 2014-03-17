package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.userstructure.UserStructureBaseBuilder;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;

public abstract class AbstractOrgUnitBuilder<T extends AbstractOrgUnitBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
  private String superOuId = null;
  private Set<IOrgUnitMember> members = new HashSet<IOrgUnitMember>();
  
  // constructors:
	public AbstractOrgUnitBuilder()
	{
		super();
	}
	
	public AbstractOrgUnitBuilder(String identifier)
	{
		super(identifier);
	}
	
	// builder-methods:
	@SuppressWarnings("unchecked")
	public T superOuId(String superOuId) {
		this.superOuId = superOuId;
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T members(Collection<IOrgUnitMember> members) {
		this.members.addAll(members);
		return (T) this;
	}

	// accessors:
	String getSuperOuId()
	{
		return superOuId;
	}
	
	Set<IOrgUnitMember> getMembers()
	{
		return members;
	}
}
