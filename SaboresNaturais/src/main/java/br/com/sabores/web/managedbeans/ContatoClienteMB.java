package br.com.sabores.web.managedbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sabores.ejb.enums.TipoContato;
import br.com.sabores.ejb.enums.TipoMensagemEmail;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.util.DateUtils;
import br.com.sabores.ejb.vo.ContatoVO;

@Named("contatoMB")
@RequestScoped
public class ContatoClienteMB extends CustomManagedBean
{
	
	private String mensagem;
	private Cliente cliente;
	private TipoContato tipoContato;
	private String email;
	private String nomeFantasia;
	private String nomeDoContato;
	
	@Inject @br.com.sabores.ejb.annotations.Email(tipoEmail=TipoMensagemEmail.COMUNICADO_PARA_ADMINISTRADOR)
	private Event<ContatoVO> disparaEmailContato;
	
	@PostConstruct
	public void init()
	{
		if(getClienteLogado() != null)
		{
			
			setCliente(getClienteLogado());
			
		}
	}
	
	public String enviarEmail()
	{
		
		disparaEmailContato.fire	(
				new ContatoVO	(
					getClienteLogado().getNomeFantasia(), 
					getClienteLogado().getNomeDoContato(),
					getEmail(),
					getMensagem(), 
					getTipoContato().getDescricao(), 
					DateUtils.retornarDataHoraAtual()
								)
									);
		showInfoMessage("", "Mensagem enviada com sucesso.");
		return "toHome";
		
	}
	
	public List<String> getListaEmailsCliente()
	{
		List<String> retorno = new ArrayList<>();
		if(getCliente() != null && getCliente().getEmail() != null)
		{
			if(getCliente().getEmail().getEmailPrincipal() != null 
					&& !getCliente().getEmail().getEmailPrincipal().equals(""))
			{
				retorno.add(getCliente().getEmail().getEmailPrincipal());
			}
			
			if(getCliente().getEmail().getEmailAlternativo1() != null 
					&& !getCliente().getEmail().getEmailAlternativo1().equals(""))
			{
				retorno.add(getCliente().getEmail().getEmailAlternativo1());
			}
			
			if(getCliente().getEmail().getEmailAlternativo2() != null 
					&& !getCliente().getEmail().getEmailAlternativo2().equals(""))
			{
				retorno.add(getCliente().getEmail().getEmailAlternativo2());
			}
			
			if(getCliente().getEmail().getEmailAlternativo3() != null 
					&& !getCliente().getEmail().getEmailAlternativo3().equals(""))
			{
				retorno.add(getCliente().getEmail().getEmailAlternativo3());
			}
			
			if(getCliente().getEmail().getEmailAlternativo4() != null 
					&& !getCliente().getEmail().getEmailAlternativo4().equals(""))
			{
				retorno.add(getCliente().getEmail().getEmailAlternativo4());
			}
		}
		
		return retorno;
		
	}
	
	public List<TipoContato> getContatos()
	{
		return new ArrayList<TipoContato>(Arrays.asList(TipoContato.values()));
	}
	
	public String getMensagem() 
	{
		return mensagem;
	}

	public void setMensagem(String mensagem) 
	{
		this.mensagem = mensagem;
	}

	public Cliente getCliente() 
	{
		return cliente;
	}

	public void setCliente(Cliente cliente) 
	{
		this.cliente = cliente;
	}

	public TipoContato getTipoContato() 
	{
		return tipoContato;
	}

	public void setTipoContato(TipoContato tipoContato) 
	{
		this.tipoContato = tipoContato;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getNomeDoContato() 
	{
		return nomeDoContato;
	}
	
	public void setNomeDoContato(String nomeDoContato) 
	{
		this.nomeDoContato = nomeDoContato;
	}
	
	public String getNomeFantasia() 
	{
		return nomeFantasia;
	}
	
	public void setNomeFantasia(String nomeFantasia) 
	{
		this.nomeFantasia = nomeFantasia;
	}
}
