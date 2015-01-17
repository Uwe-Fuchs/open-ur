package org.openur.module.service.security;


import javax.inject.Inject;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.module.service.userstructure.IUserServices;

public class AuthorizationServicesImpl
	implements IAuthorizationServices
{
	@Inject
	private IUserServices userServices;
	
	@Inject
	private IOrgUnitServices orgUnitServices;

	@Inject
	private ISecurityDomainServices securityDomainServices;

	@Override
	public Boolean hasPermission(IPerson user, IAuthorizableOrgUnit orgUnit,
		IPermission permission, IApplication app)
	{
		boolean hasPerm = false;
		IAuthorizableOrgUnit ouTmp = orgUnit;
		
		while (!hasPerm && ouTmp != null)
		{
			hasPerm = ouTmp.hasPermission(user, app, permission);
			ouTmp = ouTmp.getSuperOrgUnit();
		}
		
		return hasPerm;
	}

	@Override
	public Boolean hasPermission(String userId, String orgUnitId, String perm, IApplication app)
	{
		IPerson person = userServices.findPersonById(userId);
		IAuthorizableOrgUnit orgUnit = orgUnitServices.findOrgUnitById(orgUnitId);
		IPermission permission = securityDomainServices.findPermissionByName(perm, app);
		
		return hasPermission(person, orgUnit, permission, app);
	}
}
