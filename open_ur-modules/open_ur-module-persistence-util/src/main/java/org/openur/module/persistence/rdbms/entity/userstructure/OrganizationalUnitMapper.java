package org.openur.module.persistence.rdbms.entity.userstructure;

import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;

public class OrganizationalUnitMapper
{
	public static POrganizationalUnit mapFromImmutable(OrganizationalUnit immutable)
	{
		POrganizationalUnit persistable = new POrganizationalUnit();

		persistable.setNumber(immutable.getNumber());
		persistable.setStatus(immutable.getStatus());
		persistable.setName(immutable.getName());
		persistable.setShortName(immutable.getShortName());
		persistable.setDescription(immutable.getDescription());
		persistable.setEmailAddress(immutable.getEmailAddress() != null ? immutable.getEmailAddress().getAsPlainEMailAddress() : null);
		
		return persistable;
	}
}
