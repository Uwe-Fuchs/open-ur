package org.openur.module.service.security;


import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableTechUser;
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

	private boolean internalHasPermission(IPerson person, IAuthorizableOrgUnit orgUnit,	IPermission permission)
	{
		boolean hasPerm = false;
		IAuthorizableOrgUnit ouTmp = orgUnit;
		
		while (!hasPerm && ouTmp != null)
		{
			hasPerm = ouTmp.hasPermission(person, permission);
			ouTmp = ouTmp.getSuperOrgUnit();
		}
		
		return hasPerm;
	}
	
	private boolean lookupDomainObjectsAndCheckIfUserHasPermission(
		String userId, String permissionText, String applicationName, IAuthorizableOrgUnit orgUnit)
	{
		Validate.notEmpty(userId, "user-id must not be empty!");
		Validate.notEmpty(permissionText, "permission-text must not be empty!");
		Validate.notEmpty(applicationName, "application-name must not be empty!");
		
		IPermission permission = securityDomainServices.findPermission(permissionText, applicationName);
		Validate.notNull(
			permission, String.format("No permission found with permission-text '%s' and application-name '%s'!", permissionText, applicationName));
		
		if (orgUnit != null)
		{
			IPerson person = userServices.findPersonById(userId);
			Validate.notNull(person, String.format("No person found for ID '%s'!", userId));
			
			return internalHasPermission(person, orgUnit, permission);			
		} else
		{
			IAuthorizableTechUser techUser = userServices.findTechnicalUserById(userId);
			Validate.notNull(techUser, String.format("No technical-user found for techUserId '%s'!", techUser));
			
			return techUser.hasPermission(permission);
		}
	}

	@Override
	public Boolean hasPermission(String personId, String orgUnitId, String permissionText, String applicationName)
	{
		Validate.notEmpty(orgUnitId, "org-unit-id must not be empty!");
		
		IAuthorizableOrgUnit orgUnit = orgUnitServices.findOrgUnitById(orgUnitId, Boolean.TRUE);
		Validate.notNull(orgUnit, String.format("No org-unit found for orgUnitId '%s'!", orgUnitId));
		
		return lookupDomainObjectsAndCheckIfUserHasPermission(personId, permissionText, applicationName, orgUnit);
	}

	@Override
	public Boolean hasPermission(String personId, String permissionText, String applicationName)
	{		
		Set<IAuthorizableOrgUnit> rootOus = orgUnitServices.obtainRootOrgUnits();
		Validate.validState(!rootOus.isEmpty(), "There must be at least one root organizational-unit within the system!");
		
		// take first root-ou, for system-wide permissions are placed (redundantly) in ALL root-ou's:
		IAuthorizableOrgUnit orgUnit = rootOus.stream().findFirst().get();
		
		return lookupDomainObjectsAndCheckIfUserHasPermission(personId, permissionText, applicationName, orgUnit);
	}

	@Override
	public Boolean hasPermissionTechUser(String techUserId, String permissionText, String applicationName)
	{
		return lookupDomainObjectsAndCheckIfUserHasPermission(techUserId, permissionText, applicationName, null);
	}
}
