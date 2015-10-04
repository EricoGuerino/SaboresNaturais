package br.com.sabores.web.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import br.com.sabores.ejb.facade.LoginFacade;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Login;
import br.com.sabores.ejb.util.LoginUtils;

@SuppressWarnings("serial")
@SessionScoped
@Named("passwordMB")
public class PasswordMB extends CustomManagedBean implements Serializable
{
	private Cliente cliente;
	private Login login;
	private String passwordHeader;
	private String confirmPasswordHeader;
	
	@EJB
	private LoginFacade loginFacade;
	
	@PostConstruct
	public void init()
	{
		this.login = getLoginCliente();
		this.cliente = getClienteLogado();
	}
	
	public String alterarSenhas()
	{
		if((getPasswordHeader() != null && !getPasswordHeader().equals(""))
				&& (getConfirmPasswordHeader() != null && !getConfirmPasswordHeader().equals("")))
		{
			if(getPasswordHeader().equals(getConfirmPasswordHeader()))
			{
				this.login.setPassword(LoginUtils.criptografarSenha(getPasswordHeader()));
				getLoginFacade().alterar(this.login);
				setarLoginAndClienteNaSessao(getSessaoAtual(), this.login, this.cliente);
				showInfoMessage("SENHA ALTERADA COM SUCESSO", null);
			}
			
			else 
			{
				showErrorMessage("", "Campos 'Nova Senha' e 'Confirme Nova Senha' precisam ser iguais!");
			}
		}
		
		else 
		{
			if(getPasswordHeader() != null && !getPasswordHeader().equals(""))
			{
				showErrorMessage("", "Campo 'Nova Senha' é obrigatório!");
			}
			
			if(getConfirmPasswordHeader() != null && !getConfirmPasswordHeader().equals(""))
			{
				showErrorMessage("", "Campo 'Confirme Nova Senha' é obrigatório!");
			}
		}
		
		return null;
	}
	
	public void valueChangePassword(ValueChangeEvent event)
	{
		if(event != null)
		{
			setPasswordHeader((String)event.getNewValue());
		}
	}
	
	public void valueChangeConfirmation(ValueChangeEvent event)
	{
		if(event != null)
		{
			setConfirmPasswordHeader((String)event.getNewValue());
		}
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getPasswordHeader() 
	{
		return passwordHeader;
	}

	public void setPasswordHeader(String passwordHeader) 
	{
		this.passwordHeader = passwordHeader;
	}
	
	public LoginFacade getLoginFacade() 
	{
		return loginFacade;
	}
	
	public String getConfirmPasswordHeader() 
	{
		return confirmPasswordHeader;
	}
	
	public void setConfirmPasswordHeader(String confirmPasswordHeader) 
	{
		this.confirmPasswordHeader = confirmPasswordHeader;
	}
}
