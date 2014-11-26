package org.openur.module.persistence.rdbms.entity.userstructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.openur.module.domain.userstructure.Status;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;

@Entity(name="USER_STRUCTURE_BASE")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class PUserStructureBase
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = -1175370888285671616L;

	@Column(name="NUMBER", nullable=false, unique=true)
	private String number;
	
	@Column(name="STATUS", nullable=false)
	private Status status = Status.ACTIVE;

	// constructor:
	protected PUserStructureBase()
	{
		super();
	}

	// accessors:
	public String getNumber()
	{
		return number;
	}

	public Status getStatus()
	{
		return status;
	}

	protected void setNumber(String number)
	{
		this.number = number;
	}

	protected void setStatus(Status status)
	{
		this.status = status;
	}
}
