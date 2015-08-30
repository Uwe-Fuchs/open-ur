package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Test;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMemberTest.MyOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMemberTest.MyOrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;
import org.openur.module.util.exception.OpenURRuntimeException;

public class OrganizationalUnitTest
{
	@Test
	public void testCompareTo()
	{
		MyOrgUnit ou1 = new MyOrgUnitBuilder("numberOu1", "ou")
			.build();		
		
		MyOrgUnit ou2 = new MyOrgUnitBuilder("numberOu2", "ou")
			.build();
		
		assertTrue("ou1 should be before ou2 because of ou-numbers", ou1.compareTo(ou2) < 0);
		
		
		ou2 = new MyOrgUnitBuilder("numberOu1",  "ou")
			.status(Status.INACTIVE)
			.build();
	
		assertTrue("ou1 should be before ou2 because of status", ou1.compareTo(ou2) < 0);
		
	
		ou1 = new MyOrgUnitBuilder("numberOu1", "staff department")
			.build();
		
		ou2 = new MyOrgUnitBuilder("numberOu2", "accounts department")
			.build();
		
		assertTrue("ou1 should be after ou2 because of name", ou1.compareTo(ou2) > 0);
		
		
		ou1 = new MyOrgUnitBuilder("123abc", "staff department")
			.shortName("stf1")
			.build();
		ou2 = new MyOrgUnitBuilder("123abc", "staff department")
			.shortName("stf2")
			.build();
		
		assertTrue("ou1 should be before ou2 because of short-name", ou1.compareTo(ou2) < 0);
	}
	
	@Test
	public void testEquals()
	{
		final String IDENTIFIER_1 = "id1";
		final String IDENTIFIER_2 = "id2";
		
		MyOrgUnit orgUnit = new MyOrgUnitBuilder("numberOu1", "ou")
				.identifier(IDENTIFIER_1)
				.build();		
		
		Address address = new Address.AddressBuilder("111")
				.identifier(IDENTIFIER_1)
				.build();
		
		assertEquals(orgUnit, address);
		
		Person person = new PersonBuilder("number1", Name.create(null, null, "lastName1"))
				.identifier(IDENTIFIER_1)
				.build();
		
		assertEquals(person, address);
		assertEquals(person, orgUnit);
		
		person = new PersonBuilder("number2", Name.create(null, null, "lastName2"))
			.identifier(IDENTIFIER_2)
			.build();
		
		assertFalse(person.equals(address));
		assertFalse(person.equals(orgUnit));
	}

	@Test
	public void testIsRootOrgUnit()
	{
		MyOrgUnit rootOu = new MyOrgUnitBuilder("rootOuNumber", "rootOu")
			.build();
		assertTrue(rootOu.isRootOrgUnit());
		
		MyOrgUnit ou = new MyOrgUnitBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
		  .superOrgUnit(rootOu)
			.build();
		assertFalse(ou.isRootOrgUnit());
		
		MyOrgUnit _ou = ou.getRootOrgUnit();
		assertTrue(_ou.isRootOrgUnit());
		
		_ou = ou.getSuperOrgUnit();
		assertTrue(_ou.isRootOrgUnit());
	}

	@Test
	public void testIsMember()
	{
		Person pers1 = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		
		Person pers2 = new PersonBuilder("numberPers2", Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		MyOrgUnitMember m2 = new MyOrgUnitMemberBuilder(pers2, OU_ID).build();
		MyOrgUnitMember m1 = new MyOrgUnitMemberBuilder(pers1, OU_ID).build();	
		
		MyOrgUnit ou = new MyOrgUnitBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.myOrgUnitMembers(Arrays.asList(m1, m2))
			.build();
		
		Set<MyOrgUnitMember> _members = ou.getMembers();
		assertNotNull(_members);
		assertEquals(_members.size(), 2);
		
		assertTrue(ou.isMember(m1.getPerson()));
		assertTrue(ou.isMember(m1.getPerson().getIdentifier()));
		assertTrue(ou.isMember(m2.getPerson()));
		assertTrue(ou.isMember(m2.getPerson().getIdentifier()));
		
		Person pers3 = new PersonBuilder("numberPers3", Name.create(Gender.MALE, "Francois", "Hollande"))
			.build();
		MyOrgUnitMember m3 = new MyOrgUnitMemberBuilder(pers3, OU_ID).build();
		
		assertFalse(ou.isMember(m3.getPerson()));
		assertFalse(ou.isMember(m3.getPerson().getIdentifier()));
		
		ou = new MyOrgUnitBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.myOrgUnitMembers(Arrays.asList(m1, m2, m3))
			.build();
		
		assertTrue(ou.isMember(m3.getPerson()));
		assertTrue(ou.isMember(m3.getPerson().getIdentifier()));
	}
	
	@Test(expected=OpenURRuntimeException.class)
	public void testCreateWithWrongOrgUnitID()
	{
		Person pers = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		final String OU_ID_1 = UUID.randomUUID().toString();
		MyOrgUnitMember member = new MyOrgUnitMemberBuilder(pers, OU_ID_1).build();
		
		final String OU_ID_2 = UUID.randomUUID().toString();
		MyOrgUnitBuilder oub = new MyOrgUnitBuilder("ouNumber", "ou").identifier(OU_ID_2);
		oub.myOrgUnitMembers(Arrays.asList(member));
	}
	
	@Test
	public void testCreateWithoutMembers()
	{
		MyOrgUnit ou = new MyOrgUnitBuilder("ouNumber", "ou")
			.build();
		
		assertTrue(ou.getMembers().isEmpty());
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithNullMembersList()
	{
		new MyOrgUnitBuilder("ouNumber", "ou")
			.myOrgUnitMembers(null);
	}
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsRootWithSuperOuId()
	{
		MyOrgUnit ou = new MyOrgUnitBuilder("ouNumber", "ou")
			.build();
		
		new MyOrgUnitBuilder("superOuNumber", "superOu")
			.superOrgUnit(ou)
			.build();
	}
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsNonRootWithoutSuperOuId()
	{
		MyOrgUnit rootOu = new MyOrgUnitBuilder("rootOuNumber", "rootOu")
			.build();
		
		new MyOrgUnitBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
			.build();
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptySuperOu()
	{
		MyOrgUnit rootOu = new MyOrgUnitBuilder("rootOuNumber", "rootOu")
			.build();
		
		new MyOrgUnitBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
			.superOrgUnit(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateAsNonRootWithEmptyRoot()
	{
		new MyOrgUnitBuilder("ouNumber", "ou").rootOrgUnit(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptyNumber()
	{
		new MyOrgUnitBuilder().name("someName").build();
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptyName()
	{
		new MyOrgUnitBuilder().number("someNumber").build();
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyNumber()
	{
		new MyOrgUnitBuilder(null, "someName").build();
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyName()
	{
		new MyOrgUnitBuilder("someNumber", null).build();
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// internal implementation of AbstractOrgUnit:
	static class MyOrgUnit
		extends AbstractOrgUnit
	{
		private static final long serialVersionUID = -1856092517371886423L;
	
		// constructor:
		protected MyOrgUnit(MyOrgUnitBuilder b)
		{
			super(b);
		}
		
		// accessors:
		@Override
		public MyOrgUnit getSuperOrgUnit()
		{
			return (MyOrgUnit) super.getSuperOrgUnit();
		}
		
		@Override
		public Set<MyOrgUnitMember> getMembers()
		{
			return super.getMembers()
				.stream()
				.map(member -> (MyOrgUnitMember) member)
				.collect(Collectors.toSet()); 
		}
	
		@Override
		public MyOrgUnit getRootOrgUnit()
		{
			return (MyOrgUnit) super.getRootOrgUnit();
		}
		
		@Override
		public MyOrgUnitMember findMemberByPersonId(String id)
		{
			if (id == null)
	    {
	      return null;
	    }
	
	    for (MyOrgUnitMember m : this.getMembers())
	    {
	      if (id.equals(m.getPerson().getIdentifier()))
	      {
	        return m;
	      }
	    }
	
	    return null;
		}
	
		@Override
		public MyOrgUnitMember findMemberByPerson(IPerson person)
		{
			if (person == null)
	    {
	      return null;
	    }
	
	    return findMemberByPersonId(person.getIdentifier());
		}
	}
	
	static class MyOrgUnitBuilder
		extends AbstractOrgUnitBuilder<MyOrgUnitBuilder>
	{
		// constructors:
		public MyOrgUnitBuilder(String orgUnitNumber, String name)
		{
			super(orgUnitNumber, name);
		}
		
		public MyOrgUnitBuilder()
		{
			super();
		}
	
		// builder-methods:
		public MyOrgUnitBuilder superOrgUnit(MyOrgUnit superOrgUnit)
		{
			super.superOrgUnit(superOrgUnit);
			
			return this;
		}
	
		public MyOrgUnitBuilder rootOrgUnit(MyOrgUnit rootOrgUnit)
		{
			super.rootOrgUnit(rootOrgUnit);
			
			return this;
		}
	
		public MyOrgUnitBuilder myOrgUnitMembers(Collection<MyOrgUnitMember> members)
		{
			super.members(members);
			
			return this;
		}	
	
		// builder:
		@Override
		public MyOrgUnit build()
		{
			super.build();
			
			return new MyOrgUnit(this);
		}
	}
}
