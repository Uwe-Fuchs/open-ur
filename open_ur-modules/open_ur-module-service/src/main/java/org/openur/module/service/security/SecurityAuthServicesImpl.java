package org.openur.module.service.security;


import javax.inject.Inject;

import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IAuthenticationToken;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IPrincipalUser;
import org.openur.module.domain.security.orgunit.IAuthorizableOrgUnit;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.service.userstructure.user.IUserServices;

public class SecurityAuthServicesImpl
	implements ISecurityAuthServices
{
	private IUserServices userServices;
	private ISecurityDomainServices securityDomainServices;

	@Inject
	public void setUserServices(IUserServices userServices)
	{
		this.userServices = userServices;
	}

	@Inject
	public void setSecurityDomainServices(ISecurityDomainServices securityDomainServices)
	{
		this.securityDomainServices = securityDomainServices;
	}

	@Override
	public Boolean hasPermission(IPerson user, IAuthorizableOrgUnit orgUnit,
		IPermission permission, IApplication app)
	{
		boolean hasPerm = false;
		IAuthorizableOrgUnit ouTmp = orgUnit;
		
		while (!hasPerm && ouTmp != null)
		{
			hasPerm = ouTmp.hasPermission(user, app, permission);
			ouTmp = securityDomainServices.findAuthOrgUnitById(ouTmp.getSuperOuId());
		}
		
		return hasPerm;
	}

	@Override
	public Boolean hasPermission(String userId, String orgUnitId, String perm, IApplication app)
	{
		IPerson person = userServices.findPersonById(userId);
		IAuthorizableOrgUnit orgUnit = securityDomainServices.findAuthOrgUnitById(orgUnitId);
		IPermission permission = securityDomainServices.findPermissionByName(perm, app);
		
		return hasPermission(person, orgUnit, permission, app);
	}

	@Override
	public IPrincipalUser authenticate(IAuthenticationToken authenticationToken)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
