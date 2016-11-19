package org.openur.remoting.resource.secure_api;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.openur.module.domain.userstructure.IUserStructureBase;
import org.openur.module.service.userstructure.IUserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Priority(value = Priorities.AUTHENTICATION)
public class AuthenticationFilter_J2eePreAuth
	extends AbstractSecurityFilterBase
	implements ContainerRequestFilter
{	
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter_J2eePreAuth.class);
		
	@Inject
	private IUserServices userServices;
	
	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{		
		String userId = getUserPrincipalFromSecurityContext(requestContext);
		
		if (userId == null)
		{
			LOG.debug("Pre-Authentication failed. No user-principal found in security-context.");
			
			return;
		}
		
		IUserStructureBase user = userServices.findPersonById(userId);
		
		if (user == null)
		{
			user = userServices.findTechnicalUserById(userId);			
		}
		
		if (user == null)
		{
			return;
		}
		
		LOG.debug("User with ID [{}] is pre-authenticated.", user.getIdentifier());
	}	
}
