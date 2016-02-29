package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePwTokenProvider
extends AbstractProvider<UsernamePasswordToken>
{
	protected boolean isProvided(Class<?> type)
	{
		return UsernamePasswordToken.class.getSuperclass().isAssignableFrom(type);
	}

	@Override
	public UsernamePasswordToken readFrom(Class<UsernamePasswordToken> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);
		
		return buildGson().fromJson(result, UsernamePasswordToken.class);
	}
}
