package org.openur.module.util.processing;

import java.util.Locale;
import java.util.UUID;

import org.openur.module.util.data.PermissionScope;
import org.openur.module.util.data.Status;

/**
 * delivers all open-ur-defaults like country, status, permission-scope etc.
 * 
 * @author info@uwefuchs.com
 */
public class DefaultsUtil
{
	public static String getDefaultCountryCode()
	{
		return Locale.getDefault().getCountry();
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
