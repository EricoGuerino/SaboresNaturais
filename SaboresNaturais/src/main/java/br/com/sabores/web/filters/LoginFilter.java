package br.com.sabores.web.filters;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import br.com.sabores.ejb.facade.LoginFacade;
import br.com.sabores.ejb.model.Cliente;

@WebFilter("/loginFilter")
public class LoginFilter implements Filter {

	@EJB
	private LoginFacade loginFacade;
	
    public LoginFilter() {}
    public void destroy() {}
    public void init(FilterConfig fConfig) throws ServletException {}
    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest req = (HttpServletRequest)ec.getRequest();
		if(req.getSession().isNew())
		{
			String emailLogin = ec.getUserPrincipal().getName();
			System.out.println("email:" + emailLogin);
			Cliente clienteLogado = loginFacade.findLoginByEmail(emailLogin).getCliente();
			System.out.println("Cliente:"+clienteLogado.getRazaoSocial());
			req.getSession().setAttribute("CLIENTE_LOGADO", clienteLogado);
		}
		chain.doFilter(request, response);
	}


}
