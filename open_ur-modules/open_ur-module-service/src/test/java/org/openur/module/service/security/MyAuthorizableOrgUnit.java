package org.openur.module.service.security;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.service.userstructure.orgunit.MyOrgUnit;

public class MyAuthorizableOrgUnit
	extends MyOrgUnit
	implements IAuthorizableOrgUnit
{
	public MyAuthorizableOrgUnit(String identifier, String number)
	{
		super(identifier, number);
	}	
}
