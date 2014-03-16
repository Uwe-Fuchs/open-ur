package org.openur.module.service.security;


import javax.inject.Inject;

import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IAuthenticationToken;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
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
	public Boolean hasPermission(IPrincipalUser user, IApplication app,
		IPermission permission)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPrincipalUser authenticate(IAuthenticationToken authenticationToken)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasPermission(IPrincipalUser user, IApplication app,
		IOrganizationalUnit orgUnit, IPermission permission)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
