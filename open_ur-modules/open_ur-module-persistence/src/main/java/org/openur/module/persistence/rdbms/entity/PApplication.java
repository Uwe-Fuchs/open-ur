package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="PERSONS_APPS",
		joinColumns={@JoinColumn(name="ID_APPLICATION", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_PERSON", referencedColumnName="ID")}
	)
	private Set<PPerson> persons = new HashSet<>();

	// accessors:
	public String getApplicationName()
	{
		return applicationName;
	}

	public Set<PPerson> getPersons()
	{
		return persons;
	}

	public void setPersons(Set<PPerson> persons)
	{
		this.persons = persons;
	}

	// constructors:
	public PApplication(String applicationName)
	{
		super();
		
		Validate.notEmpty(applicationName, "application-name must not be empty!");
		this.applicationName = applicationName;
	}
	
	@SuppressWarnings("unused")
	private PApplication()
	{
		// jpa
		super();
	}
}
