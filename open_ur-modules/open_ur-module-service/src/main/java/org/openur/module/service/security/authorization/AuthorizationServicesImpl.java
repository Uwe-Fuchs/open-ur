package org.openur.module.service.security.authorization;


import javax.inject.Inject;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.module.service.userstructure.user.IUserServices;

public class AuthorizationServicesImpl
	implements IAuthorizationServices
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
}
