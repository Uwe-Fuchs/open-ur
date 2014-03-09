package org.openur.module.domain.userstructure.user.technicaluser;

import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.userstructure.IUserStructureBase;

public interface ITechnicalUser
	extends IUserStructureBase, IPrincipalUser, Comparable<ITechnicalUser>
{
	// only a marker-interface at the moment. May change in the future.
}
