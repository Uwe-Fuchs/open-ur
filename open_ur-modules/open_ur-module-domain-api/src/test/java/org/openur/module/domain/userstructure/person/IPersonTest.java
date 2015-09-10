package org.openur.module.domain.userstructure.person;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.Test;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.Status;

/**
 * @author info@uwefuchs.com
 *
 * @see PersonTest.
 */
public class IPersonTest
{
	@Test(expected= NullPointerException.class)
	public void testGetPersonalNumber()
	{
		MyPerson person = new MyPerson();
		person.setNumber(null);
		person.getPersonalNumber();
	}
	
	static class MyPerson implements IPerson
	{
		private String number = null;
		
		@Override
		public String getNumber()
		{
			return number;
		}

		public void setNumber(String number)
		{
			this.number = number;
		}

		@Override
		public Status getStatus()
		{
			return null;
		}

		@Override
		public String getIdentifier()
		{
			return null;
		}

		@Override
		public LocalDateTime getLastModifiedDate()
		{
			return null;
		}

		@Override
		public LocalDateTime getCreationDate()
		{
			return null;
		}

		@Override
		public Set<? extends IApplication> getApplications()
		{
			return null;
		}		
	}
}
