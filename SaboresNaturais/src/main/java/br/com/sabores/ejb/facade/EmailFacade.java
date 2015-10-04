package br.com.sabores.ejb.facade;

import javax.enterprise.event.Observes;

import br.com.sabores.ejb.annotations.Email;
import br.com.sabores.ejb.enums.TipoMensagemEmail;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.model.Login;
import br.com.sabores.ejb.model.MalaDireta;
import br.com.sabores.ejb.model.emailparts.EmailParts;
import br.com.sabores.ejb.vo.ContatoVO;

public interface EmailFacade extends Facade<EmailParts>
{
	void emailCompraParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.COMPRA_PARA_ADMINISTRADOR) 
			ListaDePedidos lista);
	void emailCompraParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.COMPRA_PARA_CLIENTE) 
			ListaDePedidos lista);
	void emailAlteracaoCompraParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.ALTERACAO_COMPRA_PARA_CLIENTE) 
			ListaDePedidos lista);
	void emailAlteracaoCompraParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.ALTERACAO_COMPRA_PARA_ADMINISTRADOR) 
			ListaDePedidos lista);
	void emailCancelamentoCompraParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_ADMINISTRADOR) 
			ListaDePedidos lista);
	void emailCancelamentoCompraParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.CANCELAMENTO_COMPRA_PARA_CLIENTE) 
			ListaDePedidos lista);
	void emailCadastroParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.CADASTRO_PARA_CLIENTE) 
			Login login);
	void emailCadastroParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.CADASTRO_PARA_ADMINISTRADOR) 
			Cliente cliente);
	void emailAlteracaoSenhaParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.ALTERACAO_SENHA_PARA_CLIENTE) 
			Login login);
	void emailRecuperacaoSenhaParaCliente(
			@Observes @Email(tipoEmail=TipoMensagemEmail.RECUPERACAO_SENHA_PARA_CLIENTE) 
			Login login);
	void emailComunicadoClienteParaAdministrador(
			@Observes @Email(tipoEmail=TipoMensagemEmail.COMUNICADO_PARA_ADMINISTRADOR) 
			ContatoVO contatoVO);
	void emailEnviarMalaDiretaClientes(
			@Observes @Email(tipoEmail=TipoMensagemEmail.MALA_DIRETA_PARA_CLIENTES) 
			MalaDireta mala);
	
}
