package br.com.sabores.ejb.webservices;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;


public class WSConnection 
{
	
	public String conectarWS(String api, String objetConexaoWS)
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(api + objetConexaoWS);
		Response response = target.request().get();
		
		return response.readEntity(String.class);
	}
	
}
