package org.openur.module.util.data;


public enum Gender
{
	MALE, FEMALE;

	public String getResourceBundleLookupKey()
	{
		return "gender." + this.name().toLowerCase();
	}
}
