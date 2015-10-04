package br.com.sabores.ejb.email;

public class TerraConfig extends EmailConfig
{
	private static final String HOST = "smtp.poa.terra.com.br";
	private static final String SOCKET_FACTORY_PORT = "587";
	private static final String CLAZZ = "javax.net.ssl.SSLSocketFactory";
	private static final String AUTH = "true";
	private static final String SMTP_PORT = "587";
	
	@Override
	public String getHost() 
	{
		return HOST;
	}

	@Override
	public String getSocketFactoryPort() 
	{
		return SOCKET_FACTORY_PORT;
	}

	@Override
	public String getClazz() 
	{
		return CLAZZ;
	}

	@Override
	public String getAuth() 
	{
		return AUTH;
	}

	@Override
	public String getSmtpPort() 
	{
		return SMTP_PORT;
	}
}
