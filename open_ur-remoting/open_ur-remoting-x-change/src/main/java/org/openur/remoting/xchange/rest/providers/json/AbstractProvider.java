package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.InputStream;

public class AbstractProvider
{
	protected boolean isProvided(Class<?> theirType, Class<?> myType)
	{
		return myType.isAssignableFrom(theirType);
	}
	
	protected String readFromInputStream(InputStream entityStream)
		throws IOException
	{
		StringBuilder result = new StringBuilder();

		while(true)
		{
			int i = entityStream.read();
			
			if (i < 0)
			{
				break;
			} else
			{
				result.append((char) i);
			}
		}
		
		entityStream.close();
		
		return result.toString();
	}
}
