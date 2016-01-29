package org.openur.module.persistence.rdbms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.openur.module.util.data.Status;
import org.openur.module.util.processing.DefaultsUtil;

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

	// accessors:
	protected String getNumber()
	{
		return number;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		Validate.notNull(status, "status must not be null!");
		this.status = status;
	}

	// constructors:
	protected PUserStructureBase(String number)
	{
		super();
		
		Validate.notEmpty(number, "number must not be empty!");
		this.number = number;
	}

	protected PUserStructureBase()
	{
		// JPA
		super();
	}
}
