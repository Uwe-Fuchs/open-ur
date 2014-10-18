package org.openur.module.persistence.rdbms.entity.userstructure;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;

@Entity(name="USER_STRUCTURE_BASE")
@Inheritance(strategy=InheritanceType.JOINED)
abstract class POrgUnitMember
	extends AbstractOpenUrPersistable
{
	
	
	

	protected POrgUnitMember()
	{
		super();
	}
}
