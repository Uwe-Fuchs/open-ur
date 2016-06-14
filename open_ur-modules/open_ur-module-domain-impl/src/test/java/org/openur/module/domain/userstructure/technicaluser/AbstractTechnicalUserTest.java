package org.openur.module.domain.userstructure.technicaluser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.technicaluser.AbstractTechnicalUser.AbstractTechnicalUserBuilder;
import org.openur.module.util.data.Status;

public class AbstractTechnicalUserTest
{
	@Test
	public void testCompareTo()
	{
		MyTechnicalUserBuilder tub = new MyTechnicalUserBuilder("abc");
		tub.status(Status.ACTIVE);
		MyTechnicalUser tu1 = tub.build();
		
		tub = new MyTechnicalUserBuilder("xyz");
		MyTechnicalUser tu2 = tub.build();
		
		assertTrue(tu1.compareTo(tu2) < 0);
	}
	
	private static class MyTechnicalUser
		extends AbstractTechnicalUser
	{
		private static final long serialVersionUID = 4369897120758708550L;

		public MyTechnicalUser(AbstractTechnicalUserBuilder<? extends AbstractTechnicalUserBuilder<?>> b)
		{
			super(b);
		}
	}
	
	private static class MyTechnicalUserBuilder
		extends AbstractTechnicalUserBuilder<MyTechnicalUserBuilder>
	{
		public MyTechnicalUserBuilder(String techUserNumber)
		{
			super(techUserNumber);
		}

		@Override
		protected MyTechnicalUser build()
		{
			super.build();
			
			return new MyTechnicalUser(this);
		}		
	}
}