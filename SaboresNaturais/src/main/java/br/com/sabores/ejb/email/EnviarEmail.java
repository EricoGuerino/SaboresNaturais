package br.com.sabores.ejb.email;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import br.com.sabores.ejb.model.emailparts.EmailParts;

@Dependent
public class EnviarEmail 
{
	private MimeMessage message;
	private EmailParts emailParts;
	
	@Inject
	private EmailFactory emailFactory;
	
	public void setMessage(MimeMessage message) 
	{
		this.message = message;
	}
	
	public EmailParts getEmailParts() 
	{
		if(emailParts==null)
			emailParts = new EmailParts();
		return emailParts;
	}

	public void setEmailParts(EmailParts emailParts) 
	{
		this.emailParts = emailParts;
	}

	public MimeMessage getMessage() 
	{
		if(message==null)
			message = new MimeMessage(emailFactory.getEmailSession());
		return message;
	}
	
	public void enviar(MimeMessage message)
	{
		try
		{
//			Transport.send(message);
			Transport transport = emailFactory.getEmailSession().getTransport();
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch(javax.mail.MessagingException e)
		{
			throw new RuntimeException(e);
		}
	}
	
}