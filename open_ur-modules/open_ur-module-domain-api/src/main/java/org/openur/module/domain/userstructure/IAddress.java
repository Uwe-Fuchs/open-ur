package org.openur.module.domain.userstructure;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.IIdentifiableEntity;

public interface IAddress
	extends IIdentifiableEntity, Comparable<IAddress>
{
	// properties:
	String getCareOf();

	String getPoBox();

	String getStreet();

	String getStreetNo();

	String getPostcode();

	String getCity();

	Country getCountry();
	
	// operations:
	default int compareTo(IAddress other)
	{
		int comparison = new CompareToBuilder()
											.append(this.getCountry(), other.getCountry())
											.append(this.getCity(), other.getCity())
											.append(this.getPostcode(), other.getPostcode())
											.append(this.getStreet(), other.getStreet())
											.append(this.getStreetNo(), other.getStreetNo())
											.append(this.getPoBox(), other.getPoBox())
											.append(this.getCareOf(), other.getCareOf())
											.toComparison();
		
		if (comparison != 0)
		{
			return comparison;
		}
		
		return CompareToBuilder.reflectionCompare(this, other);
	}
}