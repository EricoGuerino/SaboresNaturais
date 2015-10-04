package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.security.MessageDigest;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.sabores.ejb.enums.Perfil;
import br.com.sabores.ejb.facade.AdministradorFacade;
import br.com.sabores.ejb.facade.LoginFacade;
import br.com.sabores.ejb.model.Administrador;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Login;
import br.com.sabores.ejb.util.LoginUtils;

@SessionScoped
@Named("loginMB")
public class LoginMB extends CustomManagedBean implements Serializable
{
	private static final long serialVersionUID = 8784592784147787336L;

	@EJB
	private LoginFacade loginFacade;
	
	@EJB
	private AdministradorFacade administradorFacade;
	
	public AdministradorFacade getAdministradorFacade() 
	{
		return administradorFacade;
	}
	
	private String emailHeader;
	private String passwordHeader;
	private String emailDB;
	private String passwordDB;
	private String roleDB;
	
	private boolean logged = false;
	
	public String logar()
	{
		Administrador admin = (Administrador)getAdministradorFacade().findAdministratorByEmail(this.emailHeader);
		if(admin!=null && admin.getId()!=null)
		{
			setPasswordDB(admin.getSenha());
			String passwordHeaderCriptografado = LoginUtils.criptografarSenha(this.passwordHeader);
			setEmailDB(admin.getEmailAdministrador());
			setRoleDB(admin.getPerfil().name());
			String senhaBDCriptografada = getPasswordDB();
			if(MessageDigest.isEqual(passwordHeaderCriptografado.getBytes(), senhaBDCriptografada.getBytes())){
				setLogged(true);
				showInfoMessage("LOGIN REALIZADO COM SUCESSO", null);
	            getSessaoAtual().setAttribute("ADMINISTRADOR_LOGADO", admin);
	            return redirectAction(getRoleDB());
			} else {
				showErrorMessage("LOGIN FALHOU", "Favor verifique se email e senha foram corretamente digitados");
	            return null;
			}
		} else {
			
			Login login = (Login) getLoginFacade().findLoginByEmail(this.emailHeader); 
			Cliente clienteLogado = null;
			if(login!=null)
			{
				clienteLogado = (Cliente) login.getCliente();
			}
			if(clienteLogado == null){
				showErrorMessage("LOGIN FALHOU", "Favor verifique se email e senha foram corretamente digitados");
	            return null;
			} else {
				if(this.emailHeader.equals(clienteLogado.getEmail().getEmailPrincipal())){
					this.passwordDB = login.getPassword();
					String passwordHeaderCriptografado = LoginUtils.criptografarSenha(this.passwordHeader);
					setEmailDB(clienteLogado.getEmail().getEmailPrincipal());
					setRoleDB(clienteLogado.getPerfil());
					String senhaBDCriptografada = login.getPassword();
					if(MessageDigest.isEqual(passwordHeaderCriptografado.getBytes(), senhaBDCriptografada.getBytes())){
						setPasswordDB(login.getPassword());
						setLogged(true);
						showInfoMessage("LOGIN REALIZADO COM SUCESSO", null);
			            getSessaoAtual().setAttribute("USUARIO_LOGADO", clienteLogado);
			            getSessaoAtual().setAttribute("LOGIN_SESSAO", login);
			            return redirectAction(getRoleDB());
					} else {
						showErrorMessage("LOGIN FALHOU", "Favor verifique se email e senha foram corretamente digitados");
			            return null;
					}
				}
			}
		}
		return null;
	}
	
	public String getAdministrador()
	{
		return getAdministradorLogado().getNome();
	}
	
	public String getUsuarioRazaoSocial()
	{
		return getClienteLogado().getRazaoSocial();
	}
	
	private String redirectAction(String role)
	{
		if(role.equals(Perfil.ADMINISTRADOR.name()))
		{
			return "toAdministracao";
		} else if(role.equals(Perfil.USUARIO.name()))
		{
			return "toHome";
		}
		return null;
	}
	
	public String logoff()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) external.getRequest();
		HttpSession session = (HttpSession)request.getSession();
		session.removeAttribute("USUARIO_LOGADO");
		session.invalidate();
		setLogged(false);
		return "toSabores";
	}
	
	public boolean isLogged()
	{
		return logged;
	}
	
	public void setLogged(boolean logged)
	{
		this.logged = logged;
	}
	
	public String getEmailHeader() 
	{
		return emailHeader;
	}

	public void setEmailHeader(String emailHeader) 
	{
		this.emailHeader = emailHeader;
	}

	public String getPasswordHeader() 
	{
		return passwordHeader;
	}

	public void setPasswordHeader(String passwordHeader) 
	{
		this.passwordHeader = passwordHeader;
	}

	public String getEmailDB() 
	{
		return emailDB;
	}

	public void setEmailDB(String emailDB) 
	{
		this.emailDB = emailDB;
	}

	public String getPasswordDB() 
	{
		return passwordDB;
	}

	public void setPasswordDB(String passwordDB) 
	{
		this.passwordDB = passwordDB;
	}

	public String getRoleDB() 
	{
		return roleDB;
	}

	public void setRoleDB(String roleDB) 
	{
		this.roleDB = roleDB;
	}
	
	public LoginFacade getLoginFacade() 
	{
		return loginFacade;
	}
	
}