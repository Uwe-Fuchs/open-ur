package org.openur.module.persistence.rdbms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.util.DefaultsUtil;

@Entity(name="USER_STRUCTURE_BASE")
@Table(uniqueConstraints={@UniqueConstraint(name="UNQ_NUMBER", columnNames={"NUMBER"})})
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class PUserStructureBase
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = -1175370888285671616L;

	@Column(name="NUMBER", length=50, nullable=false)
	private String number;
	
	@Column(name="STATUS", nullable=false)
	private Status status = DefaultsUtil.getDefaultStatus();

	// constructor:
	protected PUserStructureBase()
	{
		super();
	}

	// accessors:
	protected String getNumber()
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
