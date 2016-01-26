package org.openur.module.domain.util;

import java.util.Locale;
import java.util.UUID;

import org.openur.module.domain.userstructure.Country;
import org.openur.module.util.data.PermissionScope;
import org.openur.module.util.data.Status;

/**
 * delivers all open-ur-defaults like country, status, permission-scope etc.
 * 
 * @author info@uwefuchs.com
 */
public class DefaultsUtil
{
	public static Country getDefaultCountry()
	{
		return Country.byLocale(Locale.getDefault());
	}
	
	public static String getDefaultCountryCode()
	{
		return getDefaultCountry().getCountryCode();
	}
	
	public static Status getDefaultStatus()
	{
		return Status.ACTIVE;
	}
	
	public static String getRandomIdentifierByDefaultMechanism()
	{
		return UUID.randomUUID().toString();
	}
	
	public static PermissionScope getDefaultPermissionScope()
	{
		return PermissionScope.SELECTED_SUB;
	}
}
