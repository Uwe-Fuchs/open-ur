package org.openur.module.domain.utils.compare;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.common.DomainObjectHelper;

public class PersonComparer
	extends AbstractIdentifiableObjectComparer<Person>
{
	@Override
	protected boolean internalEqualityCheck(Person person1, Person person2)
	{
		boolean isEqual = EqualsBuilder.reflectionEquals(person1, person2, Arrays.asList("applications", "homeAddress"));
		
		if (!isEqual)
		{
			return false;
		}
		
		isEqual = EqualsBuilder.reflectionEquals(person1.getHomeAddress(), person2.getHomeAddress());
		
		if (!isEqual)
		{
			return false;
		}
		
		for (OpenURApplication app : person1.getApplications())
		{
			OpenURApplication otherApp = DomainObjectHelper.findIdentifiableEntityInCollection(person2.getApplications(), app.getIdentifier());
			
			if (otherApp == null || !EqualsBuilder.reflectionEquals(app, otherApp))
			{
				return false;
			}			
		}
		
		return true;
	}
}
