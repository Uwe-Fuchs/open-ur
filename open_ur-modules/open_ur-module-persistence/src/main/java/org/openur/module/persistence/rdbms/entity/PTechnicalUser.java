package org.openur.module.persistence.rdbms.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name="TECHNICAL_USER")
public class PTechnicalUser
	extends PUserStructureBase
{
	private static final long serialVersionUID = -4445671828548847400L;
	
	@Transient
	public String getTechUserNumber()
	{
		return super.getNumber();
	}

	// constructors:
	public PTechnicalUser(String techUserNumber)
	{
		super(techUserNumber);
	}
}
