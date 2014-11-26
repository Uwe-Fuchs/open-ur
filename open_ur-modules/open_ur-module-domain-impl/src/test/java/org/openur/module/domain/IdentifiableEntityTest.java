package org.openur.module.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.UUID;

import org.junit.Test;

public class IdentifiableEntityTest
{
	@Test
	public void testEquals()
	{
		IdentifiableEntityBuilderTestImpl ieb = new IdentifiableEntityBuilderTestImpl();
		IdentifiableEntityImpl ie = new IdentifiableEntityTestImpl(ieb);
		
		ieb = new IdentifiableEntityBuilderTestImpl();
		IdentifiableEntityImpl ie2 = new IdentifiableEntityTestImpl(ieb);
		
		assertFalse(ie.equals(ie2));
	}
	
	@Test
	public void testCreate()
		throws Exception
	{
		final String ID = UUID.randomUUID().toString();				
		final LocalDateTime D = LocalDateTime.of(2012, Month.APRIL, 05, 11, 30);
		final LocalDateTime D2 = LocalDateTime.of(2012, Month.APRIL, 05, 11, 30);
		
		IdentifiableEntityBuilderTestImpl ieb = new IdentifiableEntityBuilderTestImpl().identifier(ID);
		IdentifiableEntityImpl ie = new IdentifiableEntityTestImpl(ieb);
		assertEquals("id's should be equal", ID, ie.getIdentifier());
		
		ieb = new IdentifiableEntityBuilderTestImpl().creationDate(D);
		ie = new IdentifiableEntityTestImpl(ieb);	
		assertEquals("creation dates should be equal", D2, ie.getCreationDate());
		
		ieb = new IdentifiableEntityBuilderTestImpl().identifier(ID).creationDate(D);
		ie = new IdentifiableEntityTestImpl(ieb);
		assertEquals("id's should be equal", ID, ie.getIdentifier());
		assertEquals("creation dates should be equal", D2, ie.getCreationDate());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionCreateWithEmptyId()
	{
		new IdentifiableEntityBuilderTestImpl().identifier("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionCreateWithEmptyIdAndDate()
	{
		new IdentifiableEntityBuilderTestImpl().identifier("").creationDate(LocalDateTime.now(ZoneId.systemDefault()));
	}
	
	@Test(expected=NullPointerException.class)
	public void testExceptionCreateWithEmptyIdAndEmptyDate()
	{
		new IdentifiableEntityBuilderTestImpl().identifier(null).creationDate(null);
	}

	@SuppressWarnings("serial")
	private static class IdentifiableEntityTestImpl
		extends IdentifiableEntityImpl
	{
		public IdentifiableEntityTestImpl(IdentifiableEntityBuilderTestImpl b)
		{
			super(b);
		}
	}
	
	private static class IdentifiableEntityBuilderTestImpl
		extends IdentifiableEntityBuilder<IdentifiableEntityBuilderTestImpl>
	{
		public IdentifiableEntityBuilderTestImpl()
		{
			super();
		}
	}
}
