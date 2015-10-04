package br.com.sabores.ejb.email;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import br.com.sabores.ejb.enums.TipoMensagemEmail;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.ImagemNoticia;
import br.com.sabores.ejb.model.Itens;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.model.Login;
import br.com.sabores.ejb.model.MalaDireta;
import br.com.sabores.ejb.model.Noticia;
import br.com.sabores.ejb.util.DateUtils;
import br.com.sabores.ejb.util.PrecoUTILS;
import br.com.sabores.ejb.vo.ContatoVO;

@Dependent
public class EmailBuild 
{
	
	@Inject
	private EnviarEmail enviarEmail;
	
	public EnviarEmail getEnviarEmail() 
	{
		return enviarEmail;
	}
	
	@Inject
	private EmailFactory emailFactory;
	
	public EnviarEmail buildEmailCompraParaAdministrador(ListaDePedidos lista)
	{
		
		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, 
					new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setDestinatarios(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.COMPRA_PARA_ADMINISTRADOR.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.COMPRA_PARA_ADMINISTRADOR.getDescricao());
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(emailFactory.getDadosAdministrador().getNome());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("O ");
		buildEmail.append(lista.getCliente().getRazaoSocial());
		buildEmail.append(" realizou uma compra no dia ");
		buildEmail.append(DateUtils.formatarDateTime(lista.getDataCompra()));
		buildEmail.append(", sob o n�mero de ordem ");
		buildEmail.append(lista.getId());
		buildEmail.append(".");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Nesta lista de itens, foram pedidos os seguintes produtos, pre�os e quantidades:");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;'>");
		buildEmail.append("<table style='width:80%;border: solid 1px;'>");
		buildEmail.append("<tr style='width:100%;border: solid 1px;'>");
		buildEmail.append("<td style='width:20%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>C�digo do Produto</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:50%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Produto</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:15%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Quantidade</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:15%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Pre�o</strong>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		for (Itens it : lista.getItens()) 
		{
			buildEmail.append("<tr style='width:100%;border: solid 1px;'>");
			buildEmail.append("<td style='width:20%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getProduto().getCodigoProduto());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:50%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getProduto().getProduto());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:15%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getQuantidade());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:15%;text-align:left;border: solid 1px;'>");
			buildEmail.append(PrecoUTILS.formatarDoubleParaMoeda(it.getPreco()));
			buildEmail.append("</td>");
		}
		
		buildEmail.append("</table>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<strong>");
		buildEmail.append("Total da Compra: ");
		buildEmail.append("</strong>");
		buildEmail.append(PrecoUTILS.formatarDoubleParaMoeda(PrecoUTILS.calculaSubtotal(lista)));
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr>");
		buildEmail.append("<td>");
		buildEmail.append("<br /><br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Favor aguardar contato por telefone e/ou e-mails cadastrados.");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br /><br /><br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
		
		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.COMPRA_PARA_ADMINISTRADOR);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailCompraParaCliente(ListaDePedidos lista)
	{
		
		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, new InternetAddress(lista.getCliente().getEmail().getEmailPrincipal()));
			getEnviarEmail().getEmailParts().setDestinatarios(lista.getCliente().getEmail().getEmailPrincipal());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.COMPRA_PARA_CLIENTE.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.COMPRA_PARA_CLIENTE.getDescricao());
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(lista.getCliente().getNomeFantasia());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("No dia ");
		buildEmail.append(DateUtils.formatarDateTime(lista.getDataCompra()));
		buildEmail.append(" voc�, caro cliente, realizou uma compra neste dia ");
		buildEmail.append(", sob o n�mero de ordem ");
		buildEmail.append(lista.getId());
		buildEmail.append(".");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Nesta lista de itens, foram pedidos os seguintes produtos, pre�os e quantidades:");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;'>");
		buildEmail.append("<table style='width:80%;border: solid 1px;'>");
		buildEmail.append("<tr style='width:100%;border: solid 1px;'>");
		buildEmail.append("<td style='width:20%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>C�digo do Produto</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:50%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Produto</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:15%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Quantidade</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:15%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Pre�o</strong>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		for (Itens it : lista.getItens()) 
		{
			buildEmail.append("<tr style='width:100%;border: solid 1px;'>");
			buildEmail.append("<td style='width:20%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getProduto().getCodigoProduto());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:50%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getProduto().getProduto());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:15%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getQuantidade());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:15%;text-align:left;border: solid 1px;'>");
			buildEmail.append(PrecoUTILS.formatarDoubleParaMoeda(it.getPreco()));
			buildEmail.append("</td>");
		}
		
		buildEmail.append("</table>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<strong>");
		buildEmail.append("Total da Compra: ");
		buildEmail.append("</strong>");
		buildEmail.append(PrecoUTILS.formatarDoubleParaMoeda(PrecoUTILS.calculaSubtotal(lista)));
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr>");
		buildEmail.append("<td>");
		buildEmail.append("<br /><br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Favor aguardar contato por telefone e/ou e-mails cadastrados.");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br /><br /><br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
		
		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.COMPRA_PARA_CLIENTE);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailAlteracaoCompraParaCliente(ListaDePedidos lista)
	{
		
		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, new InternetAddress(lista.getCliente().getEmail().getEmailPrincipal()));
			getEnviarEmail().getEmailParts().setDestinatarios(lista.getCliente().getEmail().getEmailPrincipal());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.ALTERACAO_COMPRA_PARA_CLIENTE.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.ALTERACAO_COMPRA_PARA_CLIENTE.getDescricao());
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(lista.getCliente().getNomeFantasia());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("No dia ");
		buildEmail.append(DateUtils.formatarDateTime(lista.getDataCompra()));
		buildEmail.append(" voc�, caro cliente, realizou uma compra neste dia ");
		buildEmail.append(", sob o n�mero de ordem ");
		buildEmail.append(lista.getId());
		buildEmail.append(".");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Atrav�s de contato entre as partes, a lista original foi alterada para os seguintes, itens, quantidades e valores:\n");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;'>");
		buildEmail.append("<table style='width:80%;border: solid 1px;'>");
		buildEmail.append("<tr style='width:100%;border: solid 1px;'>");
		buildEmail.append("<td style='width:20%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>C�digo do Produto</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:50%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Produto</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:15%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Quantidade</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:15%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Pre�o</strong>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		for (Itens it : lista.getItens()) 
		{
			buildEmail.append("<tr style='width:100%;border: solid 1px;'>");
			buildEmail.append("<td style='width:20%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getProduto().getCodigoProduto());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:50%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getProduto().getProduto());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:15%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getQuantidade());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:15%;text-align:left;border: solid 1px;'>");
			buildEmail.append(PrecoUTILS.formatarDoubleParaMoeda(it.getPreco()));
			buildEmail.append("</td>");
		}
		
		buildEmail.append("</table>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<strong>");
		buildEmail.append("Total da Compra: ");
		buildEmail.append("</strong>");
		buildEmail.append(PrecoUTILS.formatarDoubleParaMoeda(PrecoUTILS.calculaSubtotal(lista)));
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr>");
		buildEmail.append("<td>");
		buildEmail.append("<br /><br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Favor aguardar contato por telefone e/ou e-mails cadastrados.");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br /><br /><br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
		
		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.ALTERACAO_COMPRA_PARA_CLIENTE);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailAlteracaoCompraParaAdministrador(ListaDePedidos lista)
	{
		
		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setDestinatarios(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.ALTERACAO_COMPRA_PARA_ADMINISTRADOR.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.ALTERACAO_COMPRA_PARA_ADMINISTRADOR.getDescricao());
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(emailFactory.getDadosAdministrador().getNome());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("No dia ");
		buildEmail.append(DateUtils.formatarDateTime(lista.getDataCompra()));
		buildEmail.append(" o, ").append(lista.getCliente().getNomeFantasia()).append(", realizou uma compra neste dia");
		buildEmail.append(", sob o n�mero de ordem ");
		buildEmail.append(lista.getId());
		buildEmail.append(".");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Ap�s contato entre as partes, a lista original foi alterada para os seguintes, itens, quantidades e valores:\n");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;'>");
		buildEmail.append("<table style='width:80%;border: solid 1px;'>");
		buildEmail.append("<tr style='width:100%;border: solid 1px;'>");
		buildEmail.append("<td style='width:20%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>C�digo do Produto</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:50%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Produto</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:15%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Quantidade</strong>");
		buildEmail.append("</td>");
		buildEmail.append("<td style='width:15%;text-align:center;background-color:green;border: solid 1px;'>");
		buildEmail.append("<strong>Pre�o</strong>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		for (Itens it : lista.getItens()) 
		{
			buildEmail.append("<tr style='width:100%;border: solid 1px;'>");
			buildEmail.append("<td style='width:20%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getProduto().getCodigoProduto());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:50%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getProduto().getProduto());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:15%;text-align:left;border: solid 1px;'>");
			buildEmail.append(it.getQuantidade());
			buildEmail.append("</td>");
			buildEmail.append("<td style='width:15%;text-align:left;border: solid 1px;'>");
			buildEmail.append(PrecoUTILS.formatarDoubleParaMoeda(it.getPreco()));
			buildEmail.append("</td>");
		}
		
		buildEmail.append("</table>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<strong>");
		buildEmail.append("Total da Compra: ");
		buildEmail.append("</strong>");
		buildEmail.append(PrecoUTILS.formatarDoubleParaMoeda(PrecoUTILS.calculaSubtotal(lista)));
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr>");
		buildEmail.append("<td>");
		buildEmail.append("<br /><br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Favor aguardar contato por telefone e/ou e-mails cadastrados.");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br /><br /><br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
		
		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.ALTERACAO_COMPRA_PARA_ADMINISTRADOR);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getEnviarEmail();
		
	}
	
	public EnviarEmail buildEmailCancelamentoCompraParaAdministrador(ListaDePedidos lista)
	{
		
		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setDestinatarios(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_ADMINISTRADOR.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_ADMINISTRADOR.getDescricao());
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(emailFactory.getDadosAdministrador().getNome());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("No dia ");
		buildEmail.append(DateUtils.formatarDateTime(lista.getDataCompra()));
		buildEmail.append(" o, ").append(lista.getCliente().getNomeFantasia()).append(", realizou uma compra neste dia");
		buildEmail.append(", sob o n�mero de ordem ");
		buildEmail.append(lista.getId());
		buildEmail.append(".");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Ap�s contato entre as partes, a lista original foi <strong>CANCELADA</strong>\n");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
		
		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_ADMINISTRADOR);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailCancelamentoCompraParaCliente(ListaDePedidos lista)
	{

		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, new InternetAddress(lista.getCliente().getEmail().getEmailPrincipal()));
			getEnviarEmail().getEmailParts().setDestinatarios(lista.getCliente().getEmail().getEmailPrincipal());
		} 
		
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_CLIENTE.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_CLIENTE.getDescricao());
		} 
		
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(emailFactory.getDadosAdministrador().getNome());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("No dia ");
		buildEmail.append(DateUtils.formatarDateTime(lista.getDataCompra()));
		buildEmail.append(" o, ").append(lista.getCliente().getNomeFantasia()).append(", realizou uma compra neste dia");
		buildEmail.append(", sob o n�mero de ordem ");
		buildEmail.append(lista.getId());
		buildEmail.append(".");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Ap�s contato entre as partes, a lista original foi <strong>CANCELADA</strong>\n");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
		
		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_CLIENTE);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailCadastroParaCliente(Login login)
	{
		
		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} catch (AddressException e) 
		{
			e.printStackTrace();
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, 
					new InternetAddress(login.getCliente().getEmail().getEmailPrincipal()));
			getEnviarEmail().getEmailParts().setDestinatarios(login.getCliente().getEmail().getEmailPrincipal());
		} catch (AddressException e) 
		{
			e.printStackTrace();
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.CADASTRO_PARA_CLIENTE.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.CADASTRO_PARA_CLIENTE.getDescricao());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(login.getCliente().getNomeFantasia());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Seu cadastro no site Sabores Naturais foi efetivado com sucesso. ");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Para acessar nossos sistemas e fazer pedidos, voc� dever� utilizar seu email cadastrado como principal <strong>");
		buildEmail.append(login.getCliente().getEmail().getEmailPrincipal());
		buildEmail.append("</strong>.");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Para seu primeiro acesso, sua senha � ");
		buildEmail.append(login.getPassword());
		buildEmail.append('.');
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Recomenda-se que voc� efetue a troca desta.");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");

		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.CADASTRO_PARA_CLIENTE);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailCadastroParaAdministrador(Cliente cliente)
	{
		
		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} catch (AddressException e) 
		{
			e.printStackTrace();
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, 
					new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setDestinatarios(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} catch (AddressException e) 
		{
			e.printStackTrace();
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.CADASTRO_PARA_ADMINISTRADOR.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.CADASTRO_PARA_ADMINISTRADOR.getDescricao());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, Sr. ");
		buildEmail.append(emailFactory.getDadosAdministrador().getNome());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("O cadastro do cliente <strong>");
		buildEmail.append(cliente.getRazaoSocial());
		buildEmail.append("</strong> no site Sabores Naturais foi efetivado com sucesso, no dia ");
		buildEmail.append(DateUtils.formatarDate(cliente.getDataDeCadastro())).append('.');
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
		
		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.CADASTRO_PARA_ADMINISTRADOR);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailAlteracaoSenhaParaCliente(Login login)//TODO email altera��o de senha e revisar processo inteiro
	{
		StringBuilder buildEmail = new StringBuilder();
		buildEmail.append("Ol� Sr(a) ");
		buildEmail.append(login.getCliente().getRazaoSocial());
		buildEmail.append('\n');
		buildEmail.append("Sua senha no site Sabores Naturais foi alterada com sucesso!");
		buildEmail.append("Sua nova senha de acesso � ");
		buildEmail.append("\n\n");

		buildEmail.append(login.getPassword());
		buildEmail.append("\\.");
		buildEmail.append("Recomenda-se que voc� efetue a troca desta.");

		buildEmail.append("\n\n\n");
		buildEmail.append("Agradecemos a prefer�ncia � Continue conosco.");
		buildEmail.append("\n");
		buildEmail.append(emailFactory.getDadosAdministrador().getNome());
		buildEmail.append(" - ");
		buildEmail.append(emailFactory.getDadosAdministrador().getRazaoSocialEmpresa());
		buildEmail.append("\n");
		buildEmail.append(emailFactory.getDadosAdministrador().getTelefoneContato());
		
		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailRecuperacaoSenhaParaCliente(Login login)
	{
		
		MimeMultipart mime = new MimeMultipart();
		
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} catch (AddressException e) 
		{
			e.printStackTrace();
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, 
					new InternetAddress(login.getCliente().getEmail().getEmailPrincipal()));
			getEnviarEmail().getEmailParts().setDestinatarios(login.getCliente().getEmail().getEmailPrincipal());
		} catch (AddressException e) 
		{
			e.printStackTrace();
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			getEnviarEmail().getMessage().setSubject(TipoMensagemEmail.RECUPERACAO_SENHA_PARA_CLIENTE.getDescricao());
			getEnviarEmail().getEmailParts().setTitulo(TipoMensagemEmail.RECUPERACAO_SENHA_PARA_CLIENTE.getDescricao());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		StringBuilder buildEmail = new StringBuilder();
		
		buildEmail.append("Ol� Sr(a) ");
		buildEmail.append(login.getCliente().getRazaoSocial());
		buildEmail.append('\n');
		buildEmail.append("Sua senha no site Sabores Naturais foi recuperada com sucesso!");
		buildEmail.append("Sua nova senha de acesso � ");
		buildEmail.append("\n\n");

		buildEmail.append(login.getPassword());
		buildEmail.append("\\.");
		buildEmail.append("Recomenda-se que voc� efetue a troca desta.");
		
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(login.getCliente().getNomeFantasia());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("Sua senha no site Sabores Naturais foi recuperada com sucesso!");
		buildEmail.append("Sua nova senha de acesso � ");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<strong>");
		buildEmail.append(login.getPassword());
		buildEmail.append("</strong>.");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("No seu pr�ximo login, recomenda-se que voc� efetue a troca desta.");
		buildEmail.append('.');
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");

		getEmailFooter(buildEmail);
		
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			imagemBodyPart.setDataHandler(dh);
			imagemBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(emailBodyPart);
			mime.addBodyPart(imagemBodyPart);
			getEnviarEmail().getMessage().setContent(mime);
			
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.RECUPERACAO_SENHA_PARA_CLIENTE);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
		} catch (MessagingException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getEnviarEmail();
		
	}
	
	public EnviarEmail buildEmailMalaDiretaParaCliente(MalaDireta mala, Cliente cliente)
	{
	
		MimeMultipart mime = new MimeMultipart();
	
		BodyPart logoBodyPart = new MimeBodyPart();
		BodyPart emailBodyPart = new MimeBodyPart();
		BodyPart imagemBodyPart = new MimeBodyPart();
		BodyPart videoBodyPart = new MimeBodyPart();
		BodyPart imagemDiretaBodyPart = new MimeBodyPart();
		BodyPart videoDiretoBodyPart = new MimeBodyPart();		
	
		try 
		{
			getEnviarEmail().getMessage().setFrom(new InternetAddress(emailFactory.getDadosAdministrador().getEmailAdministrador()));
			getEnviarEmail().getEmailParts().setRemetente(emailFactory.getDadosAdministrador().getEmailAdministrador());
		} 
	
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
	
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	
		StringBuilder destinatarios = new StringBuilder();
		Integer count = 0;
		
		try
		{
			getEnviarEmail().getMessage().addRecipient(RecipientType.TO, new InternetAddress(cliente.getEmail().getEmailPrincipal()));
			getEnviarEmail().getEmailParts().setDestinatarios(destinatarios.toString());
		}
	
		catch (AddressException e) 
		{
			e.printStackTrace();
		} 
	
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	
		try 
		{
			getEnviarEmail().getMessage().setSubject(mala.getTitulo());
			getEnviarEmail().getEmailParts().setTitulo(mala.getTitulo());
		} 
	
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	
		StringBuilder buildEmail = new StringBuilder();
		
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
		buildEmail.append("<img src='cid:logo' style='max-width:200px;max-height:200px;'/>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h2><strong>");
		buildEmail.append("Ol�, ");
		buildEmail.append(cliente.getNomeFantasia());
		buildEmail.append("</strong></h2>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append(mala.getCorpo());
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<br />");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		
		if(mala.getImagensDiretas() != null && !mala.getImagensDiretas().isEmpty())
		{

			int countImagemDireta = 1;
			
			for(ImagemNoticia id : mala.getImagensDiretas())
			{
				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
				buildEmail.append("<img src='cid:imagem_direta"+countImagemDireta+"' style='max-width:300px;max-height:300px;'/>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");

				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;'>");
				buildEmail.append("<br />");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");
				countImagemDireta++;
			}
		}
		
		if(mala.getYoutubeDireto() != null && !mala.getYoutubeDireto().isEmpty())
		{
			buildEmail.append("<tr style='width:100%;'>");
			buildEmail.append("<td style='width:100%;'>");
			buildEmail.append("LINK V�DEO YOUTUBE: ");
			buildEmail.append("<a href='");buildEmail.append(mala.getYoutubeDireto());buildEmail.append("' target='_blank'>");
			buildEmail.append(mala.getYoutubeDireto());
			buildEmail.append("</a>");
			buildEmail.append("</td>");
			buildEmail.append("</tr>");
			
			buildEmail.append("<tr style='width:100%;'>");
			buildEmail.append("<td style='width:100%;'>");
			buildEmail.append("<br />");
			buildEmail.append("</td>");
			buildEmail.append("</tr>");
		}

		if(mala.getNoticias()!=null && !mala.getNoticias().isEmpty())
		{
			
			buildEmail.append("<tr style='width:100%;'>");
			buildEmail.append("<td style='width:100%;'>");
			buildEmail.append("<h3>Gostaria de compartilhar com voc� esta(s) not�cia(s) publicada(s) no <strong>Sabores Naturais</strong></h3>");
			buildEmail.append("</td>");
			buildEmail.append("</tr>");

			for (Noticia n : mala.getNoticias()) 
			{
				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;'>");
				buildEmail.append("<br/>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");

				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;'>");
				buildEmail.append("<h4>"+n.getTitulo()+"</h4>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");

				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;'>");
				buildEmail.append("<br/>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");

				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;align:right;'>");
				buildEmail.append("<i><strong>"+DateUtils.formatarDate(n.getDataPublicacao())+"</strong></i>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");
				
				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;'>");
				buildEmail.append("<br/>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");

				if(n.getImagem() != null && !n.getImagem().isEmpty())
				{

					for(ImagemNoticia in : n.getImagem())
					{
						buildEmail.append("<tr style='width:100%;'>");
						buildEmail.append("<td style='width:100%;text-align:center;background-color:green;'>");
						buildEmail.append("<img src='cid:noticia_"+n.getId()+"_imagem_"+in.getImagemNome().replace(" ", "")+"' style='max-width:300px;max-height:300px;'/>");
						buildEmail.append("</td>");
						buildEmail.append("</tr>");

						buildEmail.append("<tr style='width:100%;'>");
						buildEmail.append("<td style='width:100%;'>");
						buildEmail.append("<br/>");
						buildEmail.append("</td>");
						buildEmail.append("</tr>");
					}
				}
		
				if(n.getVideo() != null && n.getVideo().getUrl() != null && !n.getVideo().getUrl().equals("")) //TODO codigo embed para videos youtube
				{
					
					buildEmail.append("<tr style='width:100%;'>");
					buildEmail.append("<td style='width:100%;'>");
					buildEmail.append("LINK V�DEO YOUTUBE: ");
					buildEmail.append("<a href='");buildEmail.append(n.getVideo().getUrl());buildEmail.append("' target='_blank'>");
					buildEmail.append(n.getVideo().getUrl());
					buildEmail.append("</a>");
					buildEmail.append("</td>");
					buildEmail.append("</tr>");
					
					buildEmail.append("<tr style='width:100%;'>");
					buildEmail.append("<td style='width:100%;'>");
					buildEmail.append("<br />");
					buildEmail.append("</td>");
					buildEmail.append("</tr>");
					
				}

				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;'>");
				buildEmail.append("<br/><br/>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");
		
				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;align:justify'>");
				buildEmail.append(n.getNoticia());
				buildEmail.append("</td>");
				buildEmail.append("</tr>");

				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;'>");
				buildEmail.append("<br/>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");
				
				buildEmail.append("<tr style='width:100%;'>");
				buildEmail.append("<td style='width:100%;'>");
				buildEmail.append("LINK PARA ESTA NOT�CIA: ");
				buildEmail.append("<a href='http://localhost:8080/SaboresWeb/faces/pages/public/noticia.xhtml?noticia=");buildEmail.append(n.getId());buildEmail.append("' target='_blank'>");
				buildEmail.append("http://localhost:8080/SaboresWeb/faces/pages/public/noticia.xhtml?noticia=");buildEmail.append(n.getId());
				buildEmail.append("</a>");
				buildEmail.append("</td>");
				buildEmail.append("</tr>");

			}
		}
	
		getEmailFooter(buildEmail);
	
		try 
		{
			emailBodyPart.setContent(buildEmail.toString(), "text/html");
			mime.addBodyPart(emailBodyPart);

			DataSource ds = new ByteArrayDataSource(getClass().getResourceAsStream("/META-INF/resources/img/sabores.jpg"), "image/jpg");
			DataHandler dh = new DataHandler(ds);
			logoBodyPart.setDataHandler(dh);
			logoBodyPart.setHeader("Content-ID","<logo>");
			mime.addBodyPart(logoBodyPart);
			
			if(mala.getImagensDiretas() != null && !mala.getImagensDiretas().isEmpty())
			{

				int countImagemDireta = 1;
				DataSource ds_imagem_direta = null;
				DataHandler dh_imagem_direta = null;

				for(ImagemNoticia id : mala.getImagensDiretas())
				{
					imagemDiretaBodyPart = new MimeBodyPart();
					ds_imagem_direta = new ByteArrayDataSource(new ByteArrayInputStream(id.getImagemBytes()), "image/jpg");
					dh_imagem_direta = new DataHandler(ds_imagem_direta);
					imagemDiretaBodyPart.setDataHandler(dh_imagem_direta);
					imagemDiretaBodyPart.setHeader("Content-ID","<imagem_direta"+countImagemDireta+">");
					mime.addBodyPart(imagemDiretaBodyPart);
					countImagemDireta++;
				}
			}
			
			if(mala.getVideoDireto() != null && mala.getVideoDireto().getVideo() != null && mala.getVideoDireto().getVideo().length > 0)
			{
				videoDiretoBodyPart = new MimeBodyPart();
				DataSource ds_video_direto = new ByteArrayDataSource(new ByteArrayInputStream(mala.getVideoDireto().getVideo()), mala.getVideoDireto().getContentType());
				DataHandler dh_video_direto = new DataHandler(ds_video_direto);
				videoDiretoBodyPart.setDataHandler(dh_video_direto);
				videoDiretoBodyPart.setHeader("Content-ID","<video_direto>");
				mime.addBodyPart(videoBodyPart);
			}

			DataSource ds_imagens = null;
			DataHandler dh_imagens = null;
			DataSource ds_video = null;
			DataHandler dh_video = null;
	
			for (Noticia n : mala.getNoticias()) 
			{
				for(ImagemNoticia in : n.getImagem()) 
				{
					imagemBodyPart = new MimeBodyPart();
					ds_imagens = new ByteArrayDataSource(new ByteArrayInputStream(in.getImagemBytes()), in.getContentType());
					dh_imagens = new DataHandler(ds_imagens);
					imagemBodyPart.setDataHandler(dh_imagens);
					imagemBodyPart.setHeader("Content-ID","<noticia_"+n.getId()+"_imagem_"+in.getImagemNome().replace(" ", "")+">");
					mime.addBodyPart(imagemBodyPart);
				}
	
				if(n.getVideo() != null && n.getVideo().getVideo() != null && n.getVideo().getVideo().length > 0)
				{
					videoBodyPart = new MimeBodyPart();
					ds_video = new ByteArrayDataSource(new ByteArrayInputStream(n.getVideo().getVideo()), n.getVideo().getContentType());
					dh_video = new DataHandler(ds_imagens);
					videoBodyPart.setDataHandler(dh_imagens);
					videoBodyPart.setHeader("Content-ID","<noticia_"+n.getId()+"_video>");
					mime.addBodyPart(videoBodyPart);
				}
	
			}
	
			getEnviarEmail().getMessage().setContent(mime);
	
			getEnviarEmail().getEmailParts().setMensagem(buildEmail.toString());
			getEnviarEmail().getEmailParts().setTipoEmail(TipoMensagemEmail.MALA_DIRETA_PARA_CLIENTES);
			getEnviarEmail().getEmailParts().setDataEnvio(new Date());
	
		} 

		catch (MessagingException e) 
		{
			e.printStackTrace();
		} 

		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return getEnviarEmail();
	}
	
	public EnviarEmail buildEmailComunicadoClienteParaAdministrador(ContatoVO contatoVO)
	{
		return null;
	}
	
	private void getEmailFooter(StringBuilder buildEmail)
	{
		buildEmail.append("<table style='width:100%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;background-color:green;text-align:center;'>");
		
		buildEmail.append("<table style='width:80%;'>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h3><strong><i>");
		buildEmail.append("Agradecemos a prefer�ncia, Continue conosco.");
		buildEmail.append("</i></strong></h3>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("<tr style='width:100%;'>");
		buildEmail.append("<td style='width:100%;'>");
		buildEmail.append("<h3><strong><i>");
		buildEmail.append(emailFactory.getDadosAdministrador().getNome());
		buildEmail.append(" - ");
		buildEmail.append(emailFactory.getDadosAdministrador().getNomeFantasia());
		buildEmail.append(" - (31) ");
		buildEmail.append(emailFactory.getDadosAdministrador().getTelefoneContato());
		buildEmail.append("</i></strong></h3>");
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
		
		buildEmail.append("</td>");
		buildEmail.append("</tr>");
		buildEmail.append("</table>");
	}
	
}
