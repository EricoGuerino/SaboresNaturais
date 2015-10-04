package br.com.sabores.web.custom;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Login;
import br.gov.sintegra.ie.InscricaoEstadual;
import br.gov.sintegra.ie.InscricaoEstadualFactory;

public abstract class CustomSuperiorHelper 
{
	
	public FacesContext getFacesContext()
	{
		return FacesContext.getCurrentInstance();
	}
	
	public ExternalContext getExternalContext()
	{
		return getFacesContext().getExternalContext();
	}
	
	public HttpServletRequest getRequisicao()
	{
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	public HttpServletResponse getResposta()
	{
		return (HttpServletResponse) getExternalContext().getResponse();
	}
	
	public HttpSession getSessaoAtual()
	{
		return (HttpSession) getRequisicao().getSession();
	}
	
	public RequestContext getPrimefacesRequestContext()
	{
		return RequestContext.getCurrentInstance();
	}

	public void setarLoginAndClienteNaSessao(HttpSession session, Login login, Cliente cliente){
		session.setAttribute("USUARIO_LOGADO", cliente);
		session.setAttribute("LOGIN", login);
	}
	
	public void showInfoMessage(String summary, String detail)
	{
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}
	
	public void showWarnMessage(String summary, String detail)
	{
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}
	
	public void showErrorMessage(String summary, String detail)
	{
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}
	
	public void showFatalMessage(String summary, String detail)
	{
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
	}
	
//	public static void main(String[] args) {TESTE VALIDAÇÃO IE
//		InscricaoEstadual ie = InscricaoEstadualFactory.getInstance("MG");
//		Boolean retorno = ie.validar("002.237.281/0090");
//		System.out.println(retorno);
//	}
	
}
