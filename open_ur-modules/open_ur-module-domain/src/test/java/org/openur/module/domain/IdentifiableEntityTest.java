package org.openur.module.domain;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d = df.parse("2012-04-05");
		Date d2 = df.parse("2012-04-05");
		
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
		new IdentifiableEntityBuilderTestImpl((Date) null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testExceptionCreateWithIdAndEmptyDate()
	{
		new IdentifiableEntityBuilderTestImpl(UUID.randomUUID().toString(), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionCreateWithEmptyIdAndDate()
	{
		new IdentifiableEntityBuilderTestImpl("", new Date());
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
		
		public IdentifiableEntityBuilderTestImpl(Date creationDate)
		{
			super(creationDate);
		}
		
		public IdentifiableEntityBuilderTestImpl(String identifier, Date creationDate)
		{
			super(identifier, creationDate);
		}
	}
}
