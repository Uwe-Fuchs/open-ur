package org.openur.module.persistence.rdbms.entity.userstructure;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;

@Entity(name="ORG_UNIT_MEMBER")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class POrgUnitMember
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = -7790268803941598263L;

	// properties:
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="ORG_UNIT_ID", referencedColumnName="ID")
	private POrganizationalUnit orgUnit;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="PERSON_ID", referencedColumnName="ID")
	private PPerson person;	
	
	// accessors:
	public POrganizationalUnit getOrgUnit()
	{
		return orgUnit;
	}

	public PPerson getPerson()
	{
		return person;
	}

	protected void setOrgUnit(POrganizationalUnit orgUnit)
	{
		this.orgUnit = orgUnit;
	}

	protected void setPerson(PPerson person)
	{
		this.person = person;
	}

	// protected:
	protected POrgUnitMember()
	{
		super();
	}
}
