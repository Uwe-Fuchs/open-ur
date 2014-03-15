package org.openur.module.persistence.security;

import java.util.List;

import org.openur.module.domain.security.IRole;

public interface ISecurityDao
{
	/**
	 * searches all defined user-roles in the underlying persistence-system and returns them
	 * in a list.
	 * 
	 * @return Set with user-roles (empty if no roles are defined).
	 */
	List<IRole> obtainAllRoles();
}
