package br.com.sabores.ejb.email;

import java.util.Properties;

import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import br.com.sabores.ejb.facade.AdministradorFacade;
import br.com.sabores.ejb.model.Administrador;

@Singleton
public class EmailFactory {
	
	private Administrador dadosAdministrador;
	
	@Inject
	private AdministradorFacade administradorFacade;
	
	@Produces 
	private static Session emailSession;
	
	
	private String emailProvider(String email)
	{
		String [] email1pt = null;
		String [] email2pt = null;
		if(email.contains("@"))
		{
			email1pt = email.split("@");
			email2pt = email1pt[1].split("\\.");
		}
		return email2pt[0];
	}
	
	private EmailConfig emailProviderSelector(String email){
		return EmailConfig.emailProviderSelector(emailProvider(email));
	}
	
	public Session getEmailSession()
	{
		EmailConfig config = emailProviderSelector(getDadosAdministrador().getEmailAdministrador());
		Properties props = new Properties();
		
	    props.put("mail.smtp.host", config.getHost());
	    props.put("mail.smtp.socketFactory.port", config.getSocketFactoryPort());
	    props.put("mail.smtp.socketFactory.class", config.getClazz());
	    props.put("mail.smtp.auth", config.getAuth());
	    props.put("mail.smtp.port", config.getSmtpPort());
	
	    emailSession = Session.getInstance(props, new javax.mail.Authenticator() 
	    {
		     protected PasswordAuthentication getPasswordAuthentication() 
		     {
		    	 return new PasswordAuthentication(getDadosAdministrador().getEmailAdministrador(), getDadosAdministrador().getSenhaEmail());
		     }
	    });
	    
	    return emailSession;
	}
	
	public Administrador getDadosAdministrador() 
	{
		if(dadosAdministrador==null)
		{
			dadosAdministrador = administradorFacade.pegarDadosAdministrador();
		}
		return dadosAdministrador;
	}
}
