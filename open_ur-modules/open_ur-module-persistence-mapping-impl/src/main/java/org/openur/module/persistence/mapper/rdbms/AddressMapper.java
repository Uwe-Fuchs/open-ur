package org.openur.module.persistence.mapper.rdbms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.persistence.rdbms.entity.PAddress;

public class AddressMapper
	extends AbstractEntityMapper implements IEntityDomainObjectMapper<PAddress, Address>
{	
	@Override
	public PAddress mapFromDomainObject(Address domainObject)
	{
		PAddress persistable = new PAddress(domainObject.getPostcode());
		
		persistable.setCareOf(domainObject.getCareOf());
		persistable.setCity(domainObject.getCity());
		persistable.setPoBox(domainObject.getPoBox());
		persistable.setStreet(domainObject.getStreet());
		persistable.setStreetNo(domainObject.getStreetNo());
		persistable.setCountryCode(domainObject.getCountry() != null ? domainObject.getCountry().getCountryCode() : null);
		
		return persistable;
	}
	
	@Override
	public Address mapFromEntity(PAddress entity)
	{
		AddressBuilder immutableBuilder = new AddressBuilder(entity.getPostcode());
		
		super.mapFromEntity(immutableBuilder, entity);
		
		immutableBuilder
				.careOf(entity.getCareOf())
				.city(entity.getCity())
				.poBox(entity.getPoBox())
				.street(entity.getStreet())
				.streetNo(entity.getStreetNo())
				.country(StringUtils.isNotEmpty(entity.getCountryCode()) ? Country.byCode(entity.getCountryCode()) : null)
				.build();
		
		return immutableBuilder.build();
	}

	public static boolean domainObjectEqualsToEntity(Address domainObject, PAddress entity)
	{		
		if (!AbstractEntityMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(domainObject.getCareOf(), entity.getCareOf())
				.append(domainObject.getCity(), entity.getCity())
				.append(domainObject.getCountry() != null ? domainObject.getCountry().getCountryCode() : null, entity.getCountryCode())
				.append(domainObject.getPoBox(), entity.getPoBox())
				.append(domainObject.getPostcode(), entity.getPostcode())
				.append(domainObject.getStreet(), entity.getStreet())
				.append(domainObject.getStreetNo(), entity.getStreetNo())
				.isEquals();
	}
}
