package org.openur.module.persistence.rdbms.entity.userstructure;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistableMapper;

public class AddressMapper
{	
	public static PAddress mapFromImmutable(Address immutable)
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
		AddressBuilder immutableBuilder = new AddressBuilder(persistable.getPostcode());
		
		if (StringUtils.isNotEmpty(persistable.getIdentifier()))
		{
			immutableBuilder.identifier(persistable.getIdentifier());
		}
		
		immutableBuilder
				.careOf(persistable.getCareOf())
				.city(persistable.getCity())
				.poBox(persistable.getPoBox())
				.street(persistable.getStreet())
				.streetNo(persistable.getStreetNo())
				.country(StringUtils.isNotEmpty(persistable.getCountryCode()) ? Country.byCode(persistable.getCountryCode()) : null)
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
		
		return immutableBuilder.build();
	}
	
	public static boolean immutableEqualsToEntity(Address immutable, PAddress persistable)
	{
		if (!AbstractOpenUrPersistableMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getCareOf(), persistable.getCareOf())
				.append(immutable.getCity(), persistable.getCity())
				.append(immutable.getCountry() != null ? immutable.getCountry().getCountryCode() : null, persistable.getCountryCode())
				.append(immutable.getPoBox(), persistable.getPoBox())
				.append(immutable.getPostcode(), persistable.getPostcode())
				.append(immutable.getStreet(), persistable.getStreet())
				.append(immutable.getStreetNo(), persistable.getStreetNo())
				.isEquals();
	}
}
