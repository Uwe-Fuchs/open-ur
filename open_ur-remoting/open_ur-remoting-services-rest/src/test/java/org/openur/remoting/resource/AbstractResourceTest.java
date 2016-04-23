package org.openur.remoting.resource;

import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.openur.remoting.resource.client.AbstractResourceClient;

public abstract class AbstractResourceTest
	extends JerseyTest
{
	private final String baseUri = "http://localhost:9998/";
	private MyTestResourceClient resourceClient;

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		resourceClient = new MyTestResourceClient(getBaseURI());
	}

	protected String getBaseURI()
	{
		return baseUri;
	}

	public MyTestResourceClient getResourceClient()
	{
		return resourceClient;
	}

	protected class MyTestResourceClient
		extends AbstractResourceClient
	{
		public MyTestResourceClient(String baseUrl)
		{
			super(baseUrl);
		}

		@Override
		public <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultType, E object)
		{
			R result = super.performRestCall(url, httpMethod, acceptMediaType, contentMediaType, resultType, object);
			System.out.println("Result: " + result);

			return result;
		}

		@Override
		public <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, GenericType<R> genericResultType, E object)
		{
			R result = super.performRestCall(url, httpMethod, acceptMediaType, contentMediaType, genericResultType, object);
			System.out.println("Result: " + result);

			return result;
		}

		@Override
		public <T> T performRestCall_GET(String url, String acceptMediaType, Class<T> resultType)
		{
			T result = super.performRestCall_GET(url, acceptMediaType, resultType);
			System.out.println("Result: " + result);

			return result;
		}

		@Override
		public <T> T performRestCall_GET(String url, String acceptMediaType, GenericType<T> resultType)
		{
			T result = super.performRestCall_GET(url, acceptMediaType, resultType);
			System.out.println("Result: " + result);

			return result;
		}
	}
}
