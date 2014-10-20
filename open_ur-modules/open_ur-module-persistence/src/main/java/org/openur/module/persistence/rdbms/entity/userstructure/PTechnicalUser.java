package org.openur.module.persistence.rdbms.entity.userstructure;

import javax.persistence.Entity;

@Entity(name="TECHNICAL_USER")
public class PTechnicalUser
	extends PUserStructureBase
{
	private static final long serialVersionUID = -4445671828548847400L;

	// constructor:
	PTechnicalUser()
	{
		super();
	}
}
