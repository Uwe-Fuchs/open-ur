package org.openur.module.persistence.rdbms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="APPLICATION")
public class PApplication
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 4347267728528169905L;
	
	// properties:
	@Column(name="APPLICATION_NAME", nullable=false, unique=true)
	private String applicationName;

	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="PERSONS_APPS",
		joinColumns={@JoinColumn(name="ID_APPLICATION", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_PERSON", referencedColumnName="ID")}
	)
	private List<PPerson> persons;

	// accessors:
	public String getApplicationName()
	{
		return applicationName;
	}

	public List<PPerson> getPersons()
	{
		return persons;
	}

	void setApplicationName(String applicationName)
	{
		this.applicationName = applicationName;
	}

	void setPersons(List<PPerson> persons)
	{
		this.persons = persons;
	}

	// constructor:
	PApplication()
	{
		super();
	}
}
