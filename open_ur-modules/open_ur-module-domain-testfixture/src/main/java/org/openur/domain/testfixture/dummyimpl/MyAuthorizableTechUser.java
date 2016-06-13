package org.openur.domain.testfixture.dummyimpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IAuthorizableTechUser;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.util.data.Status;

/**
 * Simple implementation of {@link ITechnicalUser} for test-purposes.
 * 
 * @author info@uwefuchs.de
 */
public class MyAuthorizableTechUser
	implements IAuthorizableTechUser
{
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private String number;	
	private Map<MyApplicationImpl, Set<MyPermissionImpl>> permissions = new HashMap<>();	
	
	public MyAuthorizableTechUser(String identifier, String number)
	{
		super();
		
		this.identifier = identifier;
		this.number = number;
	}

	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}
	
	@Override
	public String getNumber()
	{
		return this.number;
	}
	
	public void addPermissionSet(MyApplicationImpl app, Set<MyPermissionImpl> perms)
	{
		this.permissions.put(app, perms);
	}
	
	@Override
	public boolean containsPermission(IApplication application, IPermission permission)
	{
		Set<MyPermissionImpl> perms = this.permissions.get(application);
		
		return perms != null && perms.contains(permission);
	}

	@Override
	public Status getStatus()
	{
		return null;
	}

	@Override
	public LocalDateTime getLastModifiedDate()
	{
		return null;
	}

	@Override
	public LocalDateTime getCreationDate()
	{
		return null;
	}
}