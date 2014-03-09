package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;

import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;

public interface IOrgUnitMember
	extends Comparable<IOrgUnitMember>
{
	String getPersonId();

	Set<IRole> getRoles();

	boolean hasRole(IRole role);
	
	boolean hasPermission(IApplication app, IPermission permission);
}
