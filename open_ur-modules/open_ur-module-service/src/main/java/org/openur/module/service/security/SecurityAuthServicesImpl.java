package org.openur.module.service.security;


import javax.inject.Inject;

import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IAuthenticationToken;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.persistence.security.ISecurityDao;

public class SecurityAuthServicesImpl
	implements ISecurityAuthServices
{
	private ISecurityDao securityDao;
	
	@Inject	
	public void setSecurityDao(ISecurityDao securityDao)
	{
		this.securityDao = securityDao;
	}

	@Override
	public Boolean hasPermission(IPerson user, IPermission permission, IApplication app)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasPermission(IPerson user, IOrganizationalUnit orgUnit,
		IPermission permission, IApplication app)
	{
		return Boolean.TRUE;
	}

	@Override
	public Boolean hasPermission(String userId, String permission, IApplication app)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasPermission(String userId, String orgUnitId,
		String permission, IApplication app)
	{
		return Boolean.TRUE;
	}

	@Override
	public IPrincipalUser authenticate(IAuthenticationToken authenticationToken)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
