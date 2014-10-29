package org.openur.module.domain.userstructure.technicaluser;

import org.openur.module.domain.userstructure.UserStructureBase;

public class TechnicalUser
	extends UserStructureBase
	implements ITechnicalUser
{
	private static final long serialVersionUID = 4476135911103120283L;

	TechnicalUser(TechnicalUserBuilder b)
	{
		super(b);
	}
}
