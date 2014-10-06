package org.openur.module.domain.userstructure;

import org.openur.module.domain.IIdentifiableEntity;

public interface IAddress
	extends IIdentifiableEntity, Comparable<IAddress>
{
	public abstract String getCareOf();

	public abstract String getPoBox();

	public abstract String getStreet();

	public abstract String getStreetNo();

	public abstract String getPostcode();

	public abstract String getCity();

	public abstract Country getCountry();
}