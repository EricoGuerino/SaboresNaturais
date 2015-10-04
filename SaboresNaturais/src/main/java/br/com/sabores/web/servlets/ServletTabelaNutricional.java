package br.com.sabores.web.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sabores.ejb.facade.TabelaNutricionalFacade;
import br.com.sabores.ejb.model.TabelaNutricional;

@WebServlet("/servletTabela/*")
public class ServletTabelaNutricional extends HttpServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
    
	@EJB
	private TabelaNutricionalFacade tabelaNutricionalFacade;
	
    public ServletTabelaNutricional() 
    {
        super();
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException 
    {
    	
    	Long id = Long.valueOf((String)request.getParameter("tabelaId"));
    	TabelaNutricional tabela = getTabelaNutricionalFacade().buscarUmRegistro(id);
    	
    	if(tabela!=null) 
    	{
    		response.setContentType(tabela.getTipoArquivo());
	        response.setContentLength(tabela.getTamanhoArquivo().intValue());
			response.setHeader("Content-disposition", "inline;filename="+tabela.getNomeArquivo()+"."+tabela.getExtensao());
	        OutputStream out = response.getOutputStream();
	        out.write(tabela.getTabela()); 
	        out.close();
    	}
    }
    
    public TabelaNutricionalFacade getTabelaNutricionalFacade() 
    {
		return tabelaNutricionalFacade;
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

}
