package org.openur.module.service.security;


import javax.inject.Inject;

import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IAuthenticationToken;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.service.userstructure.orgunit.IOrgUnitServices;
import org.openur.module.service.userstructure.user.IUserServices;

public class SecurityAuthServicesImpl
	implements ISecurityAuthServices
{
	private IOrgUnitServices orgUnitServices;
	private IUserServices userServices;
	private ISecurityDomainServices securityDomainServices;
	
	@Inject	
	public void setOrgUnitServices(IOrgUnitServices orgUnitServices)
	{
		this.orgUnitServices = orgUnitServices;
	}

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
	public Boolean hasPermission(IPerson user, IPermission permission, IApplication app)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasPermission(IPerson user, IOrganizationalUnit orgUnit,
		IPermission permission, IApplication app)
	{
		boolean hasPerm = false;
		IOrganizationalUnit ouTmp = orgUnit;
		
		while (!hasPerm && ouTmp != null)
		{
			hasPerm = ouTmp.hasPermission(user, app, permission);
			ouTmp = orgUnitServices.findOrgUnitById(ouTmp.getSuperOuId());
		}
		
		return hasPerm;
	}

	@Override
	public Boolean hasPermission(String userId, String perm, IApplication app)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasPermission(String userId, String orgUnitId, String perm, IApplication app)
	{
		IPerson person = userServices.findPersonById(userId);
		IOrganizationalUnit orgUnit = orgUnitServices.findOrgUnitById(orgUnitId);
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
