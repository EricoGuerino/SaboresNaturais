package br.com.sabores.ejb.email;

public class GmailConfig extends EmailConfig
{
	private static final String HOST = "smtp.gmail.com";
	private static final String SOCKET_FACTORY_PORT = "465";
	private static final String CLAZZ = "javax.net.ssl.SSLSocketFactory";
	private static final String AUTH = "true";
	private static final String SMTP_PORT = "465";
	
	public String getHost() 
	{
		return HOST;
	}
	
	public String getSocketFactoryPort() 
	{
		return SOCKET_FACTORY_PORT;
	}
	
	public String getClazz() 
	{
		return CLAZZ;
	}
	
	public String getAuth() 
	{
		return AUTH;
	}
	
	public String getSmtpPort() 
	{
		return SMTP_PORT;
	}
	
	
}
