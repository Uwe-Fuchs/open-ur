package org.openur.remoting.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

public abstract class AbstractResourceTest
{
  protected WebTarget target;
  private HttpServer server;
  
	public void setUp()
		throws Exception
	{
    // start the server
    server = Main.startServer();
    
    // create the client
    Client c = ClientBuilder.newClient();

    // uncomment the following line if you want to enable
    // support for JSON in the client (you also have to uncomment
    // dependency on jersey-media-json module in pom.xml and Main.startServer())
    // --
    // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

    target = c.target(Main.BASE_URI);
	}
	
	@SuppressWarnings("deprecation")
	public void tearDown()
		throws Exception
	{
		server.stop();
	}
}
