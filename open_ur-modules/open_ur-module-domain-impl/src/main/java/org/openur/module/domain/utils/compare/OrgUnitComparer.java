package org.openur.module.domain.utils.compare;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitFull;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;

public class OrgUnitComparer
	extends AbstractIdentifiableObjectComparer<OrgUnitFull>
{
	@Override
	protected boolean internalEqualityCheck(OrgUnitFull orgUnit1, OrgUnitFull orgUnit2)
	{
		boolean isEqual = EqualsBuilder.reflectionEquals(orgUnit1, orgUnit2, Arrays.asList("members", "rootOrgUnit", "superOrgUnit", "address"));
		
		if (!isEqual)
		{
			return false;
		}
		
		isEqual = EqualsBuilder.reflectionEquals(orgUnit1.getAddress(), orgUnit2.getAddress());
		
		if (!isEqual)
		{
			return false;
		}
		
		OrgUnitMemberComparer memberComparer = new OrgUnitMemberComparer();
		
		for (OrgUnitMember member1 : orgUnit1.getMembers())
		{
			OrgUnitMember member2 = orgUnit2.findMemberByPerson(member1.getPerson());
			
			if (member2 == null || !memberComparer.objectsAreEqual(member1, member2))
			{
				return false;
			}
		}
		
		OrgUnitComparer orgUnitComparer = new OrgUnitComparer();
		
		if (!(orgUnit1.getRootOrgUnit() == null && orgUnit2.getRootOrgUnit() == null) 
			&& !orgUnitComparer.objectsAreEqual(orgUnit1.getRootOrgUnit(), orgUnit2.getRootOrgUnit()))
		{
			return false;
		}
		
		if (!(orgUnit1.getSuperOrgUnit() == null && orgUnit2.getSuperOrgUnit() == null) 
			&& !orgUnitComparer.objectsAreEqual(orgUnit1.getSuperOrgUnit(), orgUnit2.getSuperOrgUnit()))
		{
			return false;
		}
		
		return true;
	}
}
