package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.Email;
import br.com.sabores.ejb.dao.EmailDAO;
import br.com.sabores.ejb.email.EmailBuild;
import br.com.sabores.ejb.email.EnviarEmail;
import br.com.sabores.ejb.enums.TipoMensagemEmail;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.model.Login;
import br.com.sabores.ejb.model.MalaDireta;
import br.com.sabores.ejb.model.emailparts.EmailParts;
import br.com.sabores.ejb.vo.ContatoVO;

@Stateless
public class EmailFacadeImp implements EmailFacade {
	
	@Inject
	private EmailDAO emailDAO;
	
	@Inject
	private EmailBuild build;
	
	private EmailParts emailParts;
	
	public EmailDAO getEmailDAO() 
	{
		return emailDAO;
	}

	public EmailParts getEmailParts() 
	{
		return emailParts;
	}
	
	public void setEmailParts(EmailParts emailParts) 
	{
		this.emailParts = emailParts;
	}
	
	public EmailBuild getBuild() {
		return build;
	}
	
	@Override
	public boolean salvar(EmailParts email) 
	{
		emailDAO.inserir(email);
		return true;
	}

	@Override
	public boolean apagar(EmailParts email) 
	{
		emailDAO.apagar(email.getId());
		return true;
	}

	@Override
	public boolean alterar(EmailParts email) 
	{
		emailDAO.alterar(email);
		return true;
	}

	@Override
	public EmailParts buscarUmRegistro(Long id) 
	{
		return emailDAO.findOne(id);
	}

	@Override
	public List<EmailParts> buscarTodosOsRegistros() 
	{
		return emailDAO.findAll();
	}
	
	public void emailCompraParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.COMPRA_PARA_ADMINISTRADOR) 
			ListaDePedidos lista)
	{
		EnviarEmail email = getBuild().buildEmailCompraParaAdministrador(lista);
		salvar(email.getEmailParts());
		email.enviar(email.getMessage());
	}
	
	public void emailCompraParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.COMPRA_PARA_CLIENTE) 
			ListaDePedidos lista)
	{
		EnviarEmail email = getBuild().buildEmailCompraParaCliente(lista);
		salvar(email.getEmailParts());
		email.enviar(email.getMessage());
	}
	
	public void emailAlteracaoCompraParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.ALTERACAO_COMPRA_PARA_CLIENTE) 
			ListaDePedidos lista)
	{
		
	}
	
	public void emailAlteracaoCompraParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.ALTERACAO_COMPRA_PARA_ADMINISTRADOR) 
			ListaDePedidos lista)
	{
		
	}
	
	public void emailCancelamentoCompraParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_ADMINISTRADOR) 
			ListaDePedidos lista)
	{
		
	}
	
	public void emailCancelamentoCompraParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_CLIENTE) 
			ListaDePedidos lista)
	{
		
	}
	
	public void emailCadastroParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.CADASTRO_PARA_CLIENTE) 
			Login login)
	{
		EnviarEmail email = getBuild().buildEmailCadastroParaCliente(login);
		salvar(email.getEmailParts());
		email.enviar(email.getMessage());
	}
	
	public void emailCadastroParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.CADASTRO_PARA_ADMINISTRADOR) 
			Cliente cliente)
	{
		EnviarEmail email = getBuild().buildEmailCadastroParaAdministrador(cliente);
		salvar(email.getEmailParts());
		email.enviar(email.getMessage());
	}
	
	public void emailAlteracaoSenhaParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.ALTERACAO_SENHA_PARA_CLIENTE) 
			Login login)
	{
		
	}
	
	public void emailRecuperacaoSenhaParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.RECUPERACAO_SENHA_PARA_CLIENTE) 
			Login login)
	{
		EnviarEmail email = getBuild().buildEmailRecuperacaoSenhaParaCliente(login);
		salvar(email.getEmailParts());
		email.enviar(email.getMessage());
	}
	
	public void emailComunicadoClienteParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.COMUNICADO_PARA_ADMINISTRADOR) 
			ContatoVO contatoVO) 
	{
		EnviarEmail email = getBuild().buildEmailComunicadoClienteParaAdministrador(contatoVO);
		salvar(email.getEmailParts());
		email.enviar(email.getMessage());
	}

	@Override
	public void emailEnviarMalaDiretaClientes(
			@Observes @Email(tipoEmail=TipoMensagemEmail.MALA_DIRETA_PARA_CLIENTES) 
			MalaDireta mala)
	{
		for(Cliente cliente : mala.getClientes())
		{
			EnviarEmail email = getBuild().buildEmailMalaDiretaParaCliente(mala, cliente);
			salvar(email.getEmailParts());
			email.enviar(email.getMessage());
		}
	}

}
