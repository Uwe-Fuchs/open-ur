package org.openur.module.persistence.rdbms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

@Entity(name="APPLICATION")
@Table(uniqueConstraints={@UniqueConstraint(name="UNQ_APPLICATION_NAME", columnNames={"APPLICATION_NAME"})})
public class PApplication
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 4347267728528169905L;
	
	// properties:
	@Column(name="APPLICATION_NAME", nullable=false)
	private String applicationName;

	// accessors:
	public String getApplicationName()
	{
		return applicationName;
	}

	// constructors:
	public PApplication(String applicationName)
	{
		this();
		
		Validate.notEmpty(applicationName, "application-name must not be empty!");
		this.applicationName = applicationName;
	}

	private PApplication()
	{
		// JPA
		super();
	}
}
