package org.openur.remoting.resource.secure_api;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.userstructure.IUserStructureBase;
import org.openur.module.service.userstructure.IUserServices;

@Priority(value = Priorities.AUTHENTICATION - 10)
public class AuthenticationFilter_J2eePreAuth
	extends AbstractSecurityFilterBase
	implements ContainerRequestFilter
{	
	@Inject
	private IUserServices userServices;
	
	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		SecurityContext securityContext = requestContext.getSecurityContext();
		
		if (securityContext == null)
		{
			return;
		}
		
		Principal principal = securityContext.getUserPrincipal();
		
		if (principal == null || StringUtils.isEmpty(principal.getName()))
		{
			return;
		}
		
		IUserStructureBase user = userServices.findPersonById(principal.getName());
		
		if (user == null)
		{
			user = userServices.findTechnicalUserById(principal.getName());			
		}
		
		if (user == null)
		{
			return;
		}
		
		requestContext.setProperty(USER_ID_PROPERTY, user.getIdentifier());
	}	
}
