package org.openur.module.persistence.rdbms.entity.application;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;
import org.openur.module.persistence.rdbms.entity.userstructure.PPerson;

@Entity(name="APPLICATION")
public class PApplication
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 4347267728528169905L;
	
	// properties:
	@Column(name="APPLICATION_NAME", nullable=false, unique=true)
	private String applicationName;
	
	@OneToMany(mappedBy="application", fetch=FetchType.EAGER)
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

	public static PApplication mapFromImmutable(OpenURApplication immutable)
	{
		PApplication persistable = new PApplication();		
		persistable.setApplicationName(immutable.getApplicationName());
		
		return persistable;
	}
	
	public static OpenURApplication mapToImmutable(PApplication persistable)
	{
		OpenURApplication immutable = new OpenURApplicationBuilder(persistable.getApplicationName())
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
		
		return immutable;
	}
}
