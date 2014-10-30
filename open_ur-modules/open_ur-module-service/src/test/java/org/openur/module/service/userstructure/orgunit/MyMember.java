package org.openur.module.service.userstructure.orgunit;

import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.person.IPerson;

public class MyMember
	implements IOrgUnitMember
{
	private IPerson person;
	private String orgUnitId;

	public MyMember(IPerson person, String orgUnitId)
	{
		super();
		
		this.person = person;
		this.orgUnitId = orgUnitId;
	}

	@Override
	public IPerson getPerson()
	{
		return this.person;
	}

	@Override
	public String getOrgUnitId()
	{
		return this.orgUnitId;
	}
}
