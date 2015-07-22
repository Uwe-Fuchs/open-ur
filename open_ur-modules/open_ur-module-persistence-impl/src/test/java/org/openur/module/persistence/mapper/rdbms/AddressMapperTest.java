package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.persistence.rdbms.entity.PAddress;

public class AddressMapperTest
{
	@Test
	public void testMapFromDomainObject()
	{
		Address immutable = TestObjectContainer.ADDRESS_3;
		PAddress persistable = new AddressMapper().mapFromDomainObject(immutable);
		
		assertNotNull(persistable);
		assertTrue(AddressMapper.domainObjectEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PAddress persistable = new PAddress("11");
		
		persistable.setCareOf("Schmidt");
		persistable.setCity("city_1");
		persistable.setPoBox("poBox_1");
		persistable.setStreet("street_1");
		persistable.setStreetNo("11");
		persistable.setCountryCode("DE");
		
		Address immutable = new AddressMapper().mapFromEntity(persistable);
		
		assertNotNull(immutable);
		assertTrue(AddressMapper.domainObjectEqualsToEntity(immutable, persistable));
	}
}
