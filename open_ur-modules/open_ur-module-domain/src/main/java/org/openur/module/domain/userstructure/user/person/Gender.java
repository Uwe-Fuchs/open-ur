package org.openur.module.domain.userstructure.user.person;


public enum Gender
{
	MALE, FEMALE;

	public String getResourceBundleLookupKey()
	{
		return "gender." + this.name().toLowerCase();
	}
}
