package org.openur.module.persistence.rdbms.entity.userstructure;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.IAddress;

public class AddressMapper
{	
	public static PAddress mapFromImmutable(IAddress immutable)
	{
		PAddress persistable = new PAddress();
		
		persistable.setCareOf(immutable.getCareOf());
		persistable.setCity(immutable.getCity());
		persistable.setPoBox(immutable.getPoBox());
		persistable.setPostcode(immutable.getPostcode());
		persistable.setStreet(immutable.getStreet());
		persistable.setStreetNo(immutable.getStreetNo());
		persistable.setCountryCode(immutable.getCountry() != null ? immutable.getCountry().getCountryCode() : null);
		
		return persistable;
	}
	
	public static Address mapFromEntity(PAddress persistable)
	{
		Address immutable = Address.builder()
				.careOf(persistable.getCareOf())
				.city(persistable.getCity())
				.poBox(persistable.getPoBox())
				.postcode(persistable.getPostcode())
				.street(persistable.getStreet())
				.streetNo(persistable.getStreetNo())
				.country(StringUtils.isNotEmpty(persistable.getCountryCode()) ? Country.byCode(persistable.getCountryCode()) : null)
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
		
		return immutable;
	}
}
