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
		String id = UUID.randomUUID().toString();				
		LocalDateTime d = LocalDateTime.of(2012, Month.APRIL, 05, 11, 30);
		LocalDateTime d2 = LocalDateTime.of(2012, Month.APRIL, 05, 11, 30);
		
		IdentifiableEntityBuilderTestImpl ieb = new IdentifiableEntityBuilderTestImpl(id);
		IdentifiableEntityImpl ie = new IdentifiableEntityTestImpl(ieb);
		assertEquals("id's should be equal", id, ie.getIdentifier());
		
		ieb = new IdentifiableEntityBuilderTestImpl(d);
		ie = new IdentifiableEntityTestImpl(ieb);	
		assertEquals("creation dates should be equal", d2, ie.getCreationDate());
		
		ieb = new IdentifiableEntityBuilderTestImpl(id, d);
		ie = new IdentifiableEntityTestImpl(ieb);
		assertEquals("id's should be equal", id, ie.getIdentifier());
		assertEquals("creation dates should be equal", d2, ie.getCreationDate());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionCreateWithEmptyId()
	{
		new IdentifiableEntityBuilderTestImpl("");
	}
	
	@Test(expected=NullPointerException.class)
	public void testExceptionCreateWithEmptyDate()
	{
		new IdentifiableEntityBuilderTestImpl((LocalDateTime) null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testExceptionCreateWithIdAndEmptyDate()
	{
		new IdentifiableEntityBuilderTestImpl(UUID.randomUUID().toString(), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionCreateWithEmptyIdAndDate()
	{
		new IdentifiableEntityBuilderTestImpl("", LocalDateTime.now(ZoneId.systemDefault()));
	}
	
	@Test(expected=NullPointerException.class)
	public void testExceptionCreateWithEmptyIdAndEmptyDate()
	{
		new IdentifiableEntityBuilderTestImpl(null, null);
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
		
		public IdentifiableEntityBuilderTestImpl(String identifier)
		{
			super(identifier);
		}
		
		public IdentifiableEntityBuilderTestImpl(LocalDateTime creationDate)
		{
			super(creationDate);
		}
		
		public IdentifiableEntityBuilderTestImpl(String identifier, LocalDateTime creationDate)
		{
			super(identifier, creationDate);
		}
	}
}
