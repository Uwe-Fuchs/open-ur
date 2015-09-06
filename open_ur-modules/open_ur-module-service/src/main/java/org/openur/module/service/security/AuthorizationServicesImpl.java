package org.openur.module.service.security;


import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
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

	private boolean internalHasPermission(IPerson user, IAuthorizableOrgUnit orgUnit,	IPermission permission)
	{
		boolean hasPerm = false;
		IAuthorizableOrgUnit ouTmp = orgUnit;
		
		while (!hasPerm && ouTmp != null)
		{
			hasPerm = ouTmp.hasPermission(user, permission);
			ouTmp = ouTmp.getSuperOrgUnit();
		}
		
		return hasPerm;
	}

	@Override
	public Boolean hasPermission(String userId, String orgUnitId, String permissionText, String applicationName)
	{
		Validate.notEmpty(userId, "user-id must not be empty!");
		Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");
		Validate.notEmpty(permissionText, "permission-text must not be empty!");
		Validate.notEmpty(applicationName, "application-name must not be empty!");
		
		IPerson person = userServices.findPersonById(userId);
		Validate.notNull(person, String.format("No person found for userId '%s'!", userId));
		
		IAuthorizableOrgUnit orgUnit = orgUnitServices.findOrgUnitById(orgUnitId);
		Validate.notNull(orgUnit, String.format("No org-unit found for orgUnitId '%s'!", orgUnitId));
		
		IPermission permission = securityDomainServices.findPermission(permissionText, applicationName);
		Validate.notNull(
			permission, String.format("No permission found with permission-text '%s' and application-name '%s'!", permissionText, applicationName));
		
		return internalHasPermission(person, orgUnit, permission);
	}
}
